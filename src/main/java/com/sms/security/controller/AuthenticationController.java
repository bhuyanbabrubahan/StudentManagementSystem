package com.sms.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.payload.ApiResponseDto;
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
import com.sms.security.service.AuthenticationService;
import com.sms.util.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;


	// ============================
	// REGISTER API
	// ============================

	@Operation(
	        summary = "Register Student",
	        description = "Registers a new student account in the University Management System."
	)
	@ApiResponses({

	        @ApiResponse(
	                responseCode = "201",
	                description = "Student registered successfully"
	        ),

	        @ApiResponse(
	                responseCode = "400",
	                description = "Validation failed"
	        ),

	        @ApiResponse(
	                responseCode = "409",
	                description = "Email already exists"
	        )
	})
	@PostMapping("/register")
	public ResponseEntity<ApiResponseDto<RegisterResponseDto>> register(

	        @Valid
	        @RequestBody
	        RegisterRequestDto request) {
		
		log.info("Student registration request received for email: {}", request.getEmail());

	    RegisterResponseDto response =
	            authenticationService.register(request);

	    return ResponseBuilder.success(

	            response,

	            "Student registration completed successfully.",

	            HttpStatus.CREATED
	    );
	}

	// ============================
	// LOGIN API
	// ============================

	@Operation(
	        summary = "User Login",
	        description = "Authenticates the user and returns a JWT access token."
	)
	@ApiResponses({

	        @ApiResponse(
	                responseCode = "200",
	                description = "Login successful"
	        ),

	        @ApiResponse(
	                responseCode = "401",
	                description = "Invalid email or password"
	        ),

	        @ApiResponse(
	                responseCode = "400",
	                description = "Validation failed"
	        )
	})
	@PostMapping("/login")
	public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(

	        @Valid
	        @RequestBody
	        LoginRequestDto request) {
		
		log.info("Login request received for email: {}", request.getEmail());

	    LoginResponseDto response =
	            authenticationService.login(request);

	    return ResponseBuilder.success(
	            response,
	            "Login successful.",
	            HttpStatus.OK
	    );
	}
	
	
	
	// =====================================
	// FORGOT PASSWORD
	// =====================================

	/**
	 * Generates a password reset link for the given email address.
	 * <p>
	 * For security reasons, this API always returns the same success
	 * response even if the email is not registered.
	 * This prevents User Enumeration attacks.
	 */
	@Operation(
	        summary = "Forgot Password",
	        description = "Generates a password reset link for the registered email address. "
	                + "For security reasons, the same response is returned even if the email is not registered."
	)
	@ApiResponses({

	        @ApiResponse(
	                responseCode = "200",
	                description = "Password reset email processed successfully"
	        ),

	        @ApiResponse(
	                responseCode = "400",
	                description = "Invalid request payload"
	        )
	})
	@PostMapping("/forgot-password")
	public ResponseEntity<ApiResponseDto<PasswordResetResponseDto>> forgotPassword(

	        @Valid
	        @RequestBody
	        ForgotPasswordRequestDto request) {


	    PasswordResetResponseDto response =
	            authenticationService.forgotPassword(request);


	    return ResponseBuilder.success(

	            response,

	            "If the email is registered, a password reset link has been sent.",

	            HttpStatus.OK
	    );
	}



	// =====================================
	// RESET PASSWORD
	// =====================================

	/**
	 * Resets the user's password using a valid password reset token.
	 */
	@Operation(
	        summary = "Reset Password",
	        description = "Resets the user's password using a valid, unused and non-expired password reset token."
	)
	@ApiResponses({

	        @ApiResponse(
	                responseCode = "200",
	                description = "Password reset successfully"
	        ),

	        @ApiResponse(
	                responseCode = "400",
	                description = "Invalid request, expired token, used token or password validation failed"
	        )
	})
	@PostMapping("/reset-password")
	public ResponseEntity<ApiResponseDto<PasswordResetResponseDto>> resetPassword(

	        @Valid
	        @RequestBody
	        ResetPasswordRequestDto request) {


	    PasswordResetResponseDto response =
	            authenticationService.resetPassword(request);


	    return ResponseBuilder.success(

	            response,

	            "Password reset successfully.",

	            HttpStatus.OK
	    );
	}
	
	
	
	// =====================================
	// LOGOUT
	// =====================================
	
	@Operation(
	        summary = "Logout",
	        description = "Logs out the authenticated user by removing refresh token."
	)
	@ApiResponses({

	        @ApiResponse(
	                responseCode = "200",
	                description = "Logout successful"
	        ),

	        @ApiResponse(
	                responseCode = "401",
	                description = "Unauthorized"
	        )
	})
	@PostMapping("/logout")
	public ResponseEntity<ApiResponseDto<LogoutResponseDto>> logout() {

	    log.info("Logout request received.");

	    LogoutResponseDto response =
	            authenticationService.logout();

	    return ResponseBuilder.success(
	            response,
	            "Logout successful.",
	            HttpStatus.OK
	    );
	}
	
	
	
	
	
	
	// =====================================
	// REFRESH ACCESS TOKEN
	// =====================================

	@Operation(
	        summary = "Refresh Access Token",
	        description = "Generates a new JWT access token using a valid refresh token."
	)
	@ApiResponses({

	        @ApiResponse(
	                responseCode = "200",
	                description = "Access token generated successfully"
	        ),

	        @ApiResponse(
	                responseCode = "400",
	                description = "Invalid or expired refresh token"
	        ),

	        @ApiResponse(
	                responseCode = "401",
	                description = "Unauthorized"
	        )
	})
	@PostMapping("/refresh-token")
	public ResponseEntity<ApiResponseDto<RefreshTokenResponseDto>> refreshToken(

	        @Valid
	        @RequestBody
	        RefreshTokenRequestDto request) {

	    RefreshTokenResponseDto response =
	            authenticationService.refreshToken(request);

	    return ResponseBuilder.success(

	            response,

	            "Access token generated successfully.",

	            HttpStatus.OK
	    );
	}
	
	
	

}