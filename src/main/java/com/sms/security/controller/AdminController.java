package com.sms.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sms.security.dto.AdminCreateRequestDto;
import com.sms.security.dto.AdminResponseDto;
import com.sms.security.service.AdminService;
import com.sms.payload.ApiResponse;
import com.sms.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {

		this.adminService = adminService;

	}

	@PostMapping("/create")
	public ResponseEntity<ApiResponse<AdminResponseDto>> createAdmin(

			@Valid @RequestBody AdminCreateRequestDto request

	) {

		AdminResponseDto response = adminService.createAdmin(request);

		return ResponseBuilder.success(
				response,
				"Admin Created Successfully",
				HttpStatus.CREATED

		);

	}

}