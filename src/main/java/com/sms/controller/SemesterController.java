package com.sms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sms.dto.PageResponse;
import com.sms.dto.SemesterRequestDto;
import com.sms.dto.SemesterResponseDto;
import com.sms.dto.SemesterSearchRequest;
import com.sms.payload.ApiResponse;
import com.sms.service.SemesterService;
import com.sms.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {

    private final SemesterService service;

    public SemesterController(SemesterService service) {
        this.service = service;
    }

    // CREATE

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SemesterResponseDto>> createSemester(
            @Valid
            @RequestBody SemesterRequestDto dto
    ) {

        SemesterResponseDto response =
                service.createSemester(dto);

        return ResponseBuilder.success(
                response,
                "Semester created successfully",
                HttpStatus.CREATED
        );

    }

    // GET BY ID

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY','STUDENT')")
    public ResponseEntity<ApiResponse<SemesterResponseDto>> getById(
            @PathVariable Long id
    ) {

        SemesterResponseDto response =
                service.getSemesterById(id);

        return ResponseBuilder.success(
                response,
                "Semester fetched successfully",
                HttpStatus.OK
        );

    }

    // GET ALL

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY','STUDENT')")
    public ResponseEntity<ApiResponse<PageResponse<SemesterResponseDto>>> getAll(

			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "semesterNumber") String sortBy,
			@RequestParam(defaultValue = "asc") String direction

    ) {

        PageResponse<SemesterResponseDto> response =
                service.getAllSemesters(
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseBuilder.success(
                response,
                "Semesters fetched successfully",
                HttpStatus.OK
        );

    }

    // UPDATE

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SemesterResponseDto>> update(

            @PathVariable Long id,

            @Valid
            @RequestBody SemesterRequestDto dto

    ) {

        SemesterResponseDto response =
                service.updateSemester(
                        id,
                        dto
                );

        return ResponseBuilder.success(
                response,
                "Semester updated successfully",
                HttpStatus.OK
        );

    }

    // DELETE

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(

            @PathVariable Long id

    ) {

        service.deleteSemester(id);

        return ResponseBuilder.success(
                null,
                "Semester deleted successfully",
                HttpStatus.OK
        );

    }

    // SEARCH

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<ApiResponse<PageResponse<SemesterResponseDto>>> search(

            @RequestBody SemesterSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "semesterNumber") String sortBy,
            @RequestParam(defaultValue = "asc") String direction

    ) {

        PageResponse<SemesterResponseDto> response =
                service.searchSemesters(
                        request,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseBuilder.success(
                response,
                "Semesters searched successfully",
                HttpStatus.OK
        );

    }

}