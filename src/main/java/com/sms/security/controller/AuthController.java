package com.sms.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.payload.ApiResponseDto;
import com.sms.security.dto.LoginRequestDto;
import com.sms.security.dto.LoginResponseDto;
import com.sms.security.dto.RegisterRequestDto;
import com.sms.security.dto.RegisterResponseDto;
import com.sms.security.service.AuthenticationService;
import com.sms.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationService authenticationService;

	public AuthController(AuthenticationService authenticationService) {

		this.authenticationService = authenticationService;

	}

	// ============================
	// REGISTER API
	// ============================

	@PostMapping("/register")
	public ResponseEntity<ApiResponseDto<RegisterResponseDto>> register(

			@Valid @RequestBody RegisterRequestDto request

	) {

		RegisterResponseDto response =

				authenticationService.register(request);

		return ResponseBuilder.success(

				response,
				"Student Registration Successful",
				HttpStatus.CREATED

		);

	}

	// ============================
	// LOGIN API
	// ============================

	@PostMapping("/login")
	public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(

			@Valid @RequestBody LoginRequestDto request

	) {

		LoginResponseDto response =

				authenticationService.login(request);

		return ResponseBuilder.success(
				response,
				"Login Successful",
				HttpStatus.OK

		);

	}

}