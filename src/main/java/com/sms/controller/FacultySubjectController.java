package com.sms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sms.dto.FacultySubjectRequestDto;
import com.sms.dto.FacultySubjectResponseDto;
import com.sms.dto.FacultySubjectSearchRequest;
import com.sms.dto.PageResponse;
import com.sms.payload.ApiResponseDto;
import com.sms.service.FacultySubjectService;
import com.sms.util.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/faculty-subjects")
@RequiredArgsConstructor
public class FacultySubjectController {

    private final FacultySubjectService service;

    // ==========================
    // ASSIGN SUBJECT
    // ==========================
    @PostMapping
    public ResponseEntity<ApiResponseDto<FacultySubjectResponseDto>> assignSubject(
            @Valid @RequestBody FacultySubjectRequestDto dto) {

        FacultySubjectResponseDto response =
                service.assignSubject(dto);

        return ResponseBuilder.success(
                response,
                "Subject assigned successfully.",
                HttpStatus.CREATED
        );
    }

    // ==========================
    // GET BY ID
    // ==========================
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<FacultySubjectResponseDto>> getById(
            @PathVariable Long id) {

        FacultySubjectResponseDto response =
                service.getById(id);

        return ResponseBuilder.success(
                response,
                "Faculty subject fetched successfully.",
                HttpStatus.OK
        );
    }

    // ==========================
    // GET ALL
    // ==========================
    @GetMapping
    public ResponseEntity<ApiResponseDto<PageResponse<FacultySubjectResponseDto>>> getAll(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseBuilder.success(
                service.getAll(page, size, sortBy, direction),
                "Faculty subject list fetched successfully.",
                HttpStatus.OK
        );
    }

    // ==========================
    // UPDATE
    // ==========================
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<FacultySubjectResponseDto>> update(

            @PathVariable Long id,

            @Valid @RequestBody FacultySubjectRequestDto dto) {

        return ResponseBuilder.success(
                service.update(id, dto),
                "Faculty subject updated successfully.",
                HttpStatus.OK
        );
    }

    // ==========================
    // DELETE (SOFT DELETE)
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseBuilder.success(
                null,
                "Faculty subject deleted successfully.",
                HttpStatus.OK
        );
    }

    // ==========================
    // SEARCH
    // ==========================
    @PostMapping("/search")
    public ResponseEntity<ApiResponseDto<PageResponse<FacultySubjectResponseDto>>> search(

            @RequestBody FacultySubjectSearchRequest request,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseBuilder.success(
                service.search(request, page, size, sortBy, direction),
                "Faculty subject search completed successfully.",
                HttpStatus.OK
        );
    }

}