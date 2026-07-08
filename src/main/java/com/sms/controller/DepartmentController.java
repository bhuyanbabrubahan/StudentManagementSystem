package com.sms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sms.dto.DepartmentRequestDto;
import com.sms.dto.DepartmentResponseDto;
import com.sms.dto.DepartmentSearchRequest;
import com.sms.dto.PageResponse;
import com.sms.payload.ApiResponse;
import com.sms.service.DepartmentService;
import com.sms.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	
	@PostMapping
	public ResponseEntity<ApiResponse<DepartmentResponseDto>> createDepartment(
	        @Valid @RequestBody DepartmentRequestDto dto) {

	    DepartmentResponseDto response =
	            departmentService.createDepartment(dto);

	    return ResponseBuilder.success(
	            response,
	            "Department Created Successfully",
	            HttpStatus.CREATED
	    );
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DepartmentResponseDto>> getDepartmentById(
	        @PathVariable Long id) {

	    DepartmentResponseDto response =
	            departmentService.getDepartmentById(id);

	    return ResponseBuilder.success(
	            response,
	            "Department fetched successfully",
	            HttpStatus.OK
	    );
	}

	
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<DepartmentResponseDto>>> getAllDepartments(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "asc") String direction) {

	    PageResponse<DepartmentResponseDto> response =
	            departmentService.getAllDepartments(page, size, sortBy, direction);

	    return ResponseBuilder.success(
	            response,
	            "Departments fetched successfully",
	            HttpStatus.OK
	    );
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<DepartmentResponseDto>> updateDepartment(
	        @PathVariable Long id,
	        @Valid @RequestBody DepartmentRequestDto dto) {

	    DepartmentResponseDto response =
	            departmentService.updateDepartment(id, dto);

	    return ResponseBuilder.success(
	            response,
	            "Department updated successfully",
	            HttpStatus.OK
	    );
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteDepartment(@PathVariable Long id) {

	    departmentService.deleteDepartment(id);

	    return ResponseBuilder.success(null,
	            "Department deleted successfully",
	            HttpStatus.OK
	    );
	}
	
	
	
	@PostMapping("/search")
	public ResponseEntity<ApiResponse<PageResponse<DepartmentResponseDto>>> searchDepartments(
			@RequestBody DepartmentSearchRequest request, 
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {

		PageResponse<DepartmentResponseDto> response = 
				departmentService.searchDepartments(request, page, size, 
						sortBy, direction);

		return ResponseBuilder.success(response, "Departments search result", HttpStatus.OK);
	}
	
	
	
	
}