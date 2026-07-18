package com.sms.security.serviceImpl;


import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sms.security.entity.RefreshToken;
import com.sms.security.service.RefreshTokenService;

import com.location.address.entity.Address;
import com.location.address.repository.AddressRepository;
import com.location.village.entity.Village;
import com.location.village.repository.VillageRepository;
import com.sms.email.EmailService;
import com.sms.entity.Department;
import com.sms.entity.Student;
import com.sms.enums.RecordStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.ResourceAlreadyExistsException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.mapper.StudentMapper;
import com.sms.repository.DepartmentRepository;
import com.sms.repository.StudentRepository;
import com.sms.security.dto.ForgotPasswordRequestDto;
import com.sms.security.dto.LoginRequestDto;
import com.sms.security.dto.LoginResponseDto;
import com.sms.security.dto.LogoutResponseDto;
import com.sms.security.dto.PasswordResetResponseDto;
import com.sms.security.dto.RefreshTokenRequestDto;
import com.sms.security.dto.RefreshTokenResponseDto;
import com.sms.security.dto.RegisterRequestDto;
import com.sms.security.dto.RegisterResponseDto;
import com.sms.security.dto.ResetPasswordRequestDto;
import com.sms.security.entity.PasswordResetToken;
import com.sms.security.entity.Role;
import com.sms.security.entity.User;
import com.sms.security.entity.UserStatus;
import com.sms.security.repository.PasswordResetTokenRepository;
import com.sms.security.repository.UserRepository;
import com.sms.security.service.AuthenticationService;
import com.sms.security.service.JwtService;
import com.sms.sequence.enums.ModuleType;
import com.sms.sequence.service.CodeGeneratorService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl 
        implements AuthenticationService {


	private final UserRepository userRepository;

	private final StudentRepository studentRepository;

	private final DepartmentRepository departmentRepository;

	private final VillageRepository villageRepository;

	private final AddressRepository addressRepository;

	private final PasswordEncoder passwordEncoder;

	private final StudentMapper studentMapper;

	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	private final CodeGeneratorService codeGeneratorService;
	
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	
	private final EmailService emailService;
	
	private final RefreshTokenService refreshTokenService;
	
	private static final Logger log =
	        LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    // =====================================
    // REGISTER STUDENT
    // =====================================

    @Override
    @Transactional
    public RegisterResponseDto register(
            RegisterRequestDto request) {



        // 1. Duplicate Email Check

        if (userRepository.existsByEmail(request.getEmail())) {

            throw new ResourceAlreadyExistsException(
                    "Email already exists : "
                    + request.getEmail()
            );

        }



        // 2. Department Validation

        Department department =

                departmentRepository
                .findByIdAndStatus(
                        request.getDepartmentId(),
                        RecordStatus.ACTIVE
                )

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found or inactive"
                        )
                );



		// 3. Create User

		User user = new User();

		user.setEmail(request.getEmail());

		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user.setRole(Role.STUDENT);

		user.setStatus(UserStatus.ACTIVE);

		user = userRepository.save(user);

		// 4. Create Address

		Village village =

				villageRepository.findById(request.getAddress().getVillageId())

						.orElseThrow(() -> new ResourceNotFoundException("Village not found"));

		Address address = new Address();

		address.setHouseNumber(request.getAddress().getHouseNumber());

		address.setStreet(request.getAddress().getStreet());

		address.setLandmark(request.getAddress().getLandmark());

		address.setAddressType(request.getAddress().getAddressType());

		address.setVillage(village);

		address = addressRepository.save(address);

		// 5. Create Student

		Student student =

				studentMapper.convertToEntity(request);

		student.setDepartment(department);

		student.setUser(user);

		student.setAddress(address);

		student.setRollNumber(codeGeneratorService.generateCode(ModuleType.STUDENT, "STU"));

		student.setStatus(RecordStatus.ACTIVE);

		student = studentRepository.save(student);

		// 6. Response

		return new RegisterResponseDto(

				student.getId(),

				student.getRollNumber(),

				user.getEmail(),

				"Student Registration Successful"

		);

    }





    // =====================================
    // LOGIN USER
    // =====================================

    @Override
    @Transactional
    public LoginResponseDto login(
            LoginRequestDto request) {


        /*
         * Step 1:
         * Authenticate user credentials
         */
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),

                        request.getPassword()

                )

        );



        /*
         * Step 2:
         * Fetch user from database
         */
        User user =

                userRepository
                .findByEmail(request.getEmail())

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "User not found"
                        )

                );



        /*
         * Step 3:
         * Check account status
         */
        if(user.getStatus() != UserStatus.ACTIVE) {


            throw new BusinessException(
                    "User account is inactive"
            );

        }



        /*
         * Step 4:
         * Generate JWT Access Token
         */
        String accessToken =

                jwtService.generateToken(user);



        /*
         * Step 5:
         * Generate Refresh Token
         */
        RefreshToken refreshToken =

                refreshTokenService
                .createRefreshToken(user);



        /*
         * Step 6:
         * Return response
         */
        return new LoginResponseDto(

                accessToken,

                refreshToken.getToken(),

                "Bearer",

                900,

                user.getEmail(),

                user.getRole().name()

        );

    }





    @Override
    @Transactional
    public PasswordResetResponseDto forgotPassword(
            ForgotPasswordRequestDto request) {

        // Current timestamp (single source of truth)
        LocalDateTime now = LocalDateTime.now();

        // Find user (do not reveal whether email exists)
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        /*
         * Security:
         * Never disclose whether an email is registered.
         * This prevents User Enumeration attacks.
         */
        if (user == null) {

            return new PasswordResetResponseDto(
                    "If the email is registered, a password reset link has been sent.",
                    now
            );
        }

        /*
         * Remove any previously generated reset tokens.
         * Only one active reset token should exist per user.
         */
        passwordResetTokenRepository.deleteByUserId(user.getId());

        /*
         * Generate secure reset token.
         */
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(now.plusMinutes(15))
                .used(false)
                .build();

        passwordResetTokenRepository.save(resetToken);

        /*
         * Send password reset email.
         */
        emailService.sendPasswordResetEmail(
                user.getEmail(),
                token
        );

        return new PasswordResetResponseDto(
                "If the email is registered, a password reset link has been sent.",
                now
        );
    }





    @Override
    @Transactional
    public PasswordResetResponseDto resetPassword(
            ResetPasswordRequestDto request) {

        LocalDateTime now = LocalDateTime.now();

        log.info("Password reset request received.");

        /*
         * Find reset token
         */
        PasswordResetToken resetToken = passwordResetTokenRepository
                .findByToken(request.getToken())
                .orElseThrow(() -> {
                    log.warn("Invalid password reset token.");
                    return new BusinessException("Invalid reset token.");
                });

        /*
         * Check whether token is already used.
         */
        if (resetToken.isUsed()) {

            log.warn("Password reset token already used.");

            throw new BusinessException(
                    "Reset token has already been used."
            );
        }

        /*
         * Check token expiry.
         */
        if (resetToken.getExpiryDate().isBefore(now)) {

            log.warn("Password reset token expired.");

            throw new BusinessException(
                    "Reset token has expired."
            );
        }

        /*
         * Validate password confirmation.
         */
        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

            throw new BusinessException(
                    "New password and confirm password do not match."
            );
        }

        User user = resetToken.getUser();

        /*
         * Prevent old password reuse.
         */
        if (passwordEncoder.matches(
                request.getNewPassword(),
                user.getPassword())) {

            throw new BusinessException(
                    "New password cannot be same as old password."
            );
        }

        /*
         * Encode and update password.
         */
        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        /*
         * Mark token as used.
         */
        resetToken.setUsed(true);

        passwordResetTokenRepository.save(resetToken);

        log.info(
                "Password reset successful for user: {}",
                user.getEmail()
        );

        return new PasswordResetResponseDto(

                "Password reset successfully.",

                now
        );
    }





    @Override
    @Transactional
    public LogoutResponseDto logout() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Logout failed. User not found: {}", email);
                    return new ResourceNotFoundException("User not found");
                });

        // Delete refresh token
        refreshTokenService.deleteByUser(user);

        // Clear current authentication context
        SecurityContextHolder.clearContext();

        log.info("User logged out successfully: {}", user.getEmail());

        return LogoutResponseDto.builder()
                .message("Logout successful.")
                .timestamp(LocalDateTime.now())
                .build();
    }





    @Override
    @Transactional(readOnly = true)
    public RefreshTokenResponseDto refreshToken(
            RefreshTokenRequestDto request) {

        log.info("Refresh token request received.");

        /*
         * Step 1:
         * Find Refresh Token
         */
        RefreshToken refreshToken =
                refreshTokenService.findByToken(
                        request.getRefreshToken()
                );

        /*
         * Step 2:
         * Check whether token is revoked
         */
        if (refreshToken.isRevoked()) {

            log.warn("Refresh token is revoked.");

            throw new BusinessException(
                    "Refresh token has been revoked."
            );
        }

        /*
         * Step 3:
         * Check token expiry
         */
        if (refreshTokenService.isExpired(refreshToken)) {

            log.warn("Refresh token has expired.");

            throw new BusinessException(
                    "Refresh token has expired."
            );
        }

        /*
         * Step 4:
         * Fetch associated user
         */
        User user = refreshToken.getUser();

        /*
         * Step 5:
         * Check user status
         */
        if (user.getStatus() != UserStatus.ACTIVE) {

            log.warn(
                    "Inactive user attempted refresh token login: {}",
                    user.getEmail()
            );

            throw new BusinessException(
                    "User account is inactive."
            );
        }

        /*
         * Step 6:
         * Generate new Access Token
         */
        String accessToken =
                jwtService.generateToken(user);

        log.info(
                "New access token generated for user: {}",
                user.getEmail()
        );

        /*
         * Step 7:
         * Return response
         */
        return RefreshTokenResponseDto.builder()

                .accessToken(accessToken)

                .tokenType("Bearer")

                .expiresIn(900L)

                .timestamp(LocalDateTime.now())

                .build();
    }




    
    
    

}