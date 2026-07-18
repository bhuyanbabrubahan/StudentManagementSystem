package com.sms.security.service;


import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.entity.Faculty;
import com.sms.entity.Student;
import com.sms.enums.RecordStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.repository.FacultyRepository;
import com.sms.repository.StudentRepository;
import com.sms.security.dto.ChangePasswordRequestDto;
import com.sms.security.dto.ChangePasswordResponseDto;
import com.sms.security.dto.ProfileResponseDto;
import com.sms.security.entity.AdminProfile;
import com.sms.security.entity.Role;
import com.sms.security.entity.User;
import com.sms.security.mapper.ProfileMapper;
import com.sms.security.repository.AdminProfileRepository;
import com.sms.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {


	private final UserRepository userRepository;

	private final StudentRepository studentRepository;

	private final FacultyRepository facultyRepository;

	private final ProfileMapper profileMapper;

	private final PasswordEncoder passwordEncoder;

	private final AdminProfileRepository adminProfileRepository;


    @Override
    public ProfileResponseDto getMyProfile() {



        // =====================================
        // 1. Get Logged In User Email From JWT
        // =====================================


        Authentication authentication =
                SecurityContextHolder
                .getContext()
                .getAuthentication();



        String email =
                authentication.getName();



        // =====================================
        // 2. Find User
        // =====================================


        User user =

                userRepository
                .findByEmail(email)

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );




        // =====================================
        // 3. Check Role
        // =====================================


        Role role =
                user.getRole();




        // =====================================
        // STUDENT PROFILE
        // =====================================


        if(role == Role.STUDENT) {



            Student student =

                    studentRepository
                    .findByUserIdAndStatus(
                            user.getId(),
                            RecordStatus.ACTIVE
                    )

                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Student profile not found"
                            )
                    );



            return profileMapper
                    .convertToDto(student);

        }




        // =====================================
        // FACULTY PROFILE
        // =====================================


        if(role == Role.FACULTY) {



            Faculty faculty =

                    facultyRepository
                    .findByUserIdAndStatus(
                            user.getId(),
                            RecordStatus.ACTIVE
                    )

                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Faculty profile not found"
                            )
                    );



            return profileMapper
                    .convertFacultyToDto(faculty);

        }




        if (role == Role.ADMIN) {

            AdminProfile admin = adminProfileRepository
                    .findByUserIdAndStatus(
                            user.getId(),
                            RecordStatus.ACTIVE
                    )
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Active admin profile not found"
                            ));

            return profileMapper.convertAdminToDto(admin);
        }

        throw new ResourceNotFoundException(
                "Profile not found for role : " + role
        );
    }

    
    
	 // =====================================
	 // CHANGE PASSWORD
	 // =====================================
	
	 @Override
	 @Transactional
	 public ChangePasswordResponseDto changePassword(
	         ChangePasswordRequestDto request) {
	
	
	
	     Authentication authentication =
	             SecurityContextHolder
	             .getContext()
	             .getAuthentication();
	
	
	     String email = authentication.getName();
	
	     User user = userRepository.findByEmail(email)
	             .orElseThrow(() ->
	                     new ResourceNotFoundException(
	                             "User not found"
	                     ));
	
	     boolean currentPasswordMatch =
	             passwordEncoder.matches(
	                     request.getCurrentPassword(),
	                     user.getPassword()
	             );
	
	
	
	     if (!currentPasswordMatch) {
	
	         throw new BusinessException(
	                 "Current password is incorrect."
	         );
	     }
	
	
	
	     boolean newPasswordSame =
	             passwordEncoder.matches(
	                     request.getNewPassword(),
	                     user.getPassword()
	             );
	
	
	
	     if (newPasswordSame) {
	
	         throw new BusinessException(
	                 "New password cannot be same as current password."
	         );
	     }
	
	
	
	     boolean confirmPasswordMatch =
	             request.getNewPassword()
	             .equals(request.getConfirmPassword());
	
	
	     if (!confirmPasswordMatch) {
	
	         throw new BusinessException(
	                 "New password and confirm password do not match."
	         );
	     }
	
	
	     String encodedPassword =
	             passwordEncoder.encode(
	                     request.getNewPassword()
	             );
	
	
	     user.setPassword(encodedPassword);
	
	     userRepository.save(user);
	
	     return new ChangePasswordResponseDto(
	             "Password changed successfully.",
	             LocalDateTime.now()
	     );
	 }

}