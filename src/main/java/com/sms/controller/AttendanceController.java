package com.sms.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sms.dto.AttendanceRequestDto;
import com.sms.dto.AttendanceResponseDto;
import com.sms.dto.AttendanceSearchRequest;
import com.sms.dto.PageResponse;
import com.sms.payload.ApiResponseDto;
import com.sms.service.AttendanceService;
import com.sms.util.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController {


    private final AttendanceService service;



    // ==========================
    // CREATE
    // ==========================

    @PostMapping
    public ResponseEntity<ApiResponseDto<AttendanceResponseDto>> createAttendance(

            @Valid
            @RequestBody AttendanceRequestDto dto

    ){

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<AttendanceResponseDto>> getAttendanceById(

            @PathVariable Long id

    ){

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

    @GetMapping
    public ResponseEntity<ApiResponseDto<PageResponse<AttendanceResponseDto>>> getAllAttendance(

            @RequestParam(defaultValue = "0")
            int page,


            @RequestParam(defaultValue = "10")
            int size,


            @RequestParam(defaultValue = "id")
            String sortBy,


            @RequestParam(defaultValue = "asc")
            String direction

    ){


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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<AttendanceResponseDto>> updateAttendance(

            @PathVariable Long id,


            @Valid
            @RequestBody AttendanceRequestDto dto

    ){


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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteAttendance(

            @PathVariable Long id

    ){


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

	@PostMapping("/search")
	public ResponseEntity<ApiResponseDto<PageResponse<AttendanceResponseDto>>> searchAttendance(@Valid

	@RequestBody AttendanceSearchRequest request,

			@RequestParam(defaultValue = "0") int page,

			@RequestParam(defaultValue = "10") int size,

			@RequestParam(defaultValue = "id") String sortBy,

			@RequestParam(defaultValue = "asc") String direction

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