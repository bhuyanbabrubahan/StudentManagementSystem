package com.sms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sms.dto.PageResponse;
import com.sms.dto.SubjectRequestDto;
import com.sms.dto.SubjectResponseDto;
import com.sms.dto.SubjectSearchRequest;
import com.sms.payload.ApiResponseDto;
import com.sms.service.SubjectService;
import com.sms.util.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService service;

    // ==========================
    // CREATE SUBJECT
    // ==========================

    @PostMapping
    public ResponseEntity<ApiResponseDto<SubjectResponseDto>> createSubject(
            @Valid @RequestBody SubjectRequestDto dto) {

        SubjectResponseDto response =
                service.createSubject(dto);

        return ResponseBuilder.success(
                response,
                "Subject created successfully",
                HttpStatus.CREATED
        );
    }

    // ==========================
    // GET BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<SubjectResponseDto>> getSubjectById(
            @PathVariable Long id) {

        SubjectResponseDto response =
                service.getSubjectById(id);

        return ResponseBuilder.success(
                response,
                "Subject fetched successfully",
                HttpStatus.OK
        );
    }

    // ==========================
    // GET ALL
    // ==========================

    @GetMapping
    public ResponseEntity<ApiResponseDto<PageResponse<SubjectResponseDto>>> getAllSubjects(
            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction
    ) {

        PageResponse<SubjectResponseDto> response =
                service.getAllSubjects(
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseBuilder.success(
                response,
                "Subjects fetched successfully",
                HttpStatus.OK
        );
    }

    // ==========================
    // UPDATE
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<SubjectResponseDto>> updateSubject(

            @PathVariable Long id,

            @Valid @RequestBody SubjectRequestDto dto
    ) {

        SubjectResponseDto response =
                service.updateSubject(
                        id,
                        dto
                );

        return ResponseBuilder.success(
                response,
                "Subject updated successfully",
                HttpStatus.OK
        );
    }

    // ==========================
    // DELETE
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteSubject(
            @PathVariable Long id
    ) {

        service.deleteSubject(id);

        return ResponseBuilder.success(
                null,
                "Subject deleted successfully",
                HttpStatus.OK
        );
    }

    // ==========================
    // SEARCH
    // ==========================

    @PostMapping("/search")
    public ResponseEntity<ApiResponseDto<PageResponse<SubjectResponseDto>>> searchSubjects(

			@RequestBody SubjectSearchRequest request,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction

    ) {

        PageResponse<SubjectResponseDto> response =
                service.searchSubjects(
                        request,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseBuilder.success(
                response,
                "Subjects searched successfully",
                HttpStatus.OK
        );
    }

}