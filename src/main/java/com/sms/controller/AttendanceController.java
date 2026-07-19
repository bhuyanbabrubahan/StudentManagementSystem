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
import com.sms.audit.annotation.AuditLog;
import com.sms.audit.enums.AuditAction;

import com.sms.dto.AttendanceRequestDto;
import com.sms.dto.AttendanceResponseDto;
import com.sms.dto.AttendanceSearchRequest;
import com.sms.dto.PageResponse;
import com.sms.payload.ApiResponseDto;
import com.sms.service.AttendanceService;
import com.sms.util.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/attendances")
@Tag(
        name = "Attendance Management",
        description = "APIs for managing student attendance including create, retrieve, update, delete and dynamic search operations."
)
@RequiredArgsConstructor
public class AttendanceController {


    private final AttendanceService service;



	 // ==========================
	 // CREATE
	 // ==========================
	
	 @Operation(
	         summary = "Create Attendance",
	         description = """
	                 Creates a new attendance record for a student.
	
	                 Business Validations:
	                 - Student must exist and be ACTIVE
	                 - Faculty Subject Mapping must exist and be ACTIVE
	                 - Duplicate attendance is not allowed
	                 - Attendance date cannot be in the future
	                 - Attendance date must be within the semester duration
	                 """
	 )
	 @ApiResponses({
	         @ApiResponse(
	                 responseCode = "201",
	                 description = "Attendance created successfully"
	         ),
	         @ApiResponse(
	                 responseCode = "400",
	                 description = "Invalid request data or business validation failed"
	         ),
	         @ApiResponse(
	                 responseCode = "404",
	                 description = "Student or Faculty Subject Mapping not found"
	         ),
	         @ApiResponse(
	                 responseCode = "409",
	                 description = "Attendance already exists for the given student and date"
	         )
	 })
	 @AuditLog(
		        action = AuditAction.CREATE,
		        module = "ATTENDANCE",
		        description = "Create attendance"
		)
		@PostMapping
	 public ResponseEntity<ApiResponseDto<AttendanceResponseDto>> createAttendance(
	
	         @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                 description = "Attendance information",
	                 required = true
	         )
	         @Valid
	         @RequestBody AttendanceRequestDto dto
	
	 ) {
	
	     AttendanceResponseDto response =
	             service.createAttendance(dto);
	
	     return ResponseBuilder.success(
	             response,
	             "Attendance created successfully",
	             HttpStatus.CREATED
	     );
	
	 }




	// ==========================
	// GET BY ID
	// ==========================

	@Operation(
	        summary = "Get Attendance By ID",
	        description = "Retrieves an active attendance record using its unique ID."
	)
	@ApiResponses({
	        @ApiResponse(
	                responseCode = "200",
	                description = "Attendance fetched successfully"
	        ),
	        @ApiResponse(
	                responseCode = "404",
	                description = "Attendance record not found"
	        )
	})
	@GetMapping("/{id:\\d+}")
	public ResponseEntity<ApiResponseDto<AttendanceResponseDto>> getAttendanceById(

	        @Parameter(
	                description = "Unique Attendance ID",
	                example = "1",
	                required = true
	        )
	        @PathVariable Long id

	) {

	    AttendanceResponseDto response =
	            service.getAttendanceById(id);

	    return ResponseBuilder.success(
	            response,
	            "Attendance fetched successfully",
	            HttpStatus.OK
	    );

	}




	// ==========================
	// GET ALL
	// ==========================

	@Operation(
	        summary = "Get All Attendance",
	        description = """
	                Retrieves all active attendance records.

	                Features:
	                - Pagination
	                - Sorting
	                - Default sorting by ID
	                """
	)
	@ApiResponses({
	        @ApiResponse(
	                responseCode = "200",
	                description = "Attendance records fetched successfully"
	        )
	})
	@GetMapping
	public ResponseEntity<ApiResponseDto<PageResponse<AttendanceResponseDto>>> getAllAttendance(

	        @Parameter(
	                description = "Page number",
	                example = "0"
	        )
	        @RequestParam(defaultValue = "0")
	        int page,

	        @Parameter(
	                description = "Number of records per page",
	                example = "10"
	        )
	        @RequestParam(defaultValue = "10")
	        int size,

	        @Parameter(
	                description = "Field used for sorting",
	                example = "id"
	        )
	        @RequestParam(defaultValue = "id")
	        String sortBy,

	        @Parameter(
	                description = "Sorting direction",
	                example = "asc"
	        )
	        @RequestParam(defaultValue = "asc")
	        String direction

	) {

	    PageResponse<AttendanceResponseDto> response =
	            service.getAllAttendance(
	                    page,
	                    size,
	                    sortBy,
	                    direction
	            );

	    return ResponseBuilder.success(
	            response,
	            "Attendance fetched successfully",
	            HttpStatus.OK
	    );

	}




	// ==========================
	// UPDATE
	// ==========================

	@Operation(
	        summary = "Update Attendance",
	        description = """
	                Updates an existing attendance record.

	                Business Validations:
	                - Attendance record must exist
	                - Student must exist and be ACTIVE
	                - Faculty Subject Mapping must exist and be ACTIVE
	                - Duplicate attendance is not allowed
	                - Attendance date cannot be in the future
	                - Attendance date must be within the semester duration
	                """
	)
	@ApiResponses({
	        @ApiResponse(
	                responseCode = "200",
	                description = "Attendance updated successfully"
	        ),
	        @ApiResponse(
	                responseCode = "400",
	                description = "Invalid request data or business validation failed"
	        ),
	        @ApiResponse(
	                responseCode = "404",
	                description = "Attendance, Student or Faculty Subject Mapping not found"
	        ),
	        @ApiResponse(
	                responseCode = "409",
	                description = "Attendance already exists for the given student and date"
	        )
	})
	@AuditLog(
	        action = AuditAction.UPDATE,
	        module = "ATTENDANCE",
	        description = "Update attendance"
	)
	@PutMapping("/{id:\\d+}")
	public ResponseEntity<ApiResponseDto<AttendanceResponseDto>> updateAttendance(

	        @Parameter(
	                description = "Unique Attendance ID",
	                example = "1",
	                required = true
	        )
	        @PathVariable Long id,

	        @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                description = "Updated attendance information",
	                required = true
	        )
	        @Valid
	        @RequestBody AttendanceRequestDto dto

	) {

	    AttendanceResponseDto response =
	            service.updateAttendance(
	                    id,
	                    dto
	            );

	    return ResponseBuilder.success(
	            response,
	            "Attendance updated successfully",
	            HttpStatus.OK
	    );

	}




	// ==========================
	// DELETE
	// ==========================

	@Operation(
	        summary = "Delete Attendance",
	        description = "Soft deletes an attendance record."
	)
	@ApiResponses({
	        @ApiResponse(
	                responseCode = "200",
	                description = "Attendance deleted successfully"
	        ),
	        @ApiResponse(
	                responseCode = "404",
	                description = "Attendance record not found"
	        )
	})
	@AuditLog(
	        action = AuditAction.DELETE,
	        module = "ATTENDANCE",
	        description = "Delete attendance"
	)
	@DeleteMapping("/{id:\\d+}")
	public ResponseEntity<ApiResponseDto<Void>> deleteAttendance(

	        @Parameter(
	                description = "Unique Attendance ID",
	                example = "1",
	                required = true
	        )
	        @PathVariable Long id

	) {

	    service.deleteAttendance(id);

	    return ResponseBuilder.success(
	            null,
	            "Attendance deleted successfully",
	            HttpStatus.OK
	    );

	}





	// ==========================
	// SEARCH
	// ==========================

	@Operation(
	        summary = "Search Attendance",
	        description = """
	                Performs dynamic attendance search using optional filters.

	                Supported Filters:
	                - Student ID
	                - Faculty ID
	                - Subject ID
	                - Faculty Subject Mapping ID
	                - Attendance Date
	                - Attendance Status
	                - Record Status

	                Features:
	                - Dynamic Specification
	                - Pagination
	                - Sorting
	                - Case-insensitive search where applicable
	                """
	)
	@ApiResponses({
	        @ApiResponse(
	                responseCode = "200",
	                description = "Attendance search completed successfully"
	        )
	})
	@PostMapping("/search")
	public ResponseEntity<ApiResponseDto<PageResponse<AttendanceResponseDto>>> searchAttendance(

	        @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                description = "Attendance search criteria",
	                required = true
	        )
	        @RequestBody AttendanceSearchRequest request,

	        @Parameter(
	                description = "Page number",
	                example = "0"
	        )
	        @RequestParam(defaultValue = "0")
	        int page,

	        @Parameter(
	                description = "Number of records per page",
	                example = "10"
	        )
	        @RequestParam(defaultValue = "10")
	        int size,

	        @Parameter(
	                description = "Field used for sorting",
	                example = "id"
	        )
	        @RequestParam(defaultValue = "id")
	        String sortBy,

	        @Parameter(
	                description = "Sorting direction",
	                example = "asc"
	        )
	        @RequestParam(defaultValue = "asc")
	        String direction

	) {

	    PageResponse<AttendanceResponseDto> response =
	            service.searchAttendance(
	                    request,
	                    page,
	                    size,
	                    sortBy,
	                    direction
	            );

	    return ResponseBuilder.success(
	            response,
	            "Attendance searched successfully",
	            HttpStatus.OK
	    );

	}

}