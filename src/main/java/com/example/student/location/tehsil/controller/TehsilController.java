package com.example.student.location.tehsil.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.student.location.tehsil.dto.TehsilRequestDto;
import com.example.student.location.tehsil.dto.TehsilResponseDto;
import com.example.student.location.tehsil.dto.TehsilSearchDto;
import com.example.student.location.tehsil.service.TehsilService;
import com.example.student.payload.ApiResponse;
import com.example.student.util.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tehsils")
@RequiredArgsConstructor
@Validated
public class TehsilController {

    private final TehsilService tehsilService;

    @PostMapping
    public ResponseEntity<ApiResponse<TehsilResponseDto>> createTehsil(
            @Valid @RequestBody TehsilRequestDto dto) {

        TehsilResponseDto response =
                tehsilService.createTehsil(dto);

        return ResponseBuilder.success(
                response,
                "Tehsil created successfully.",
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TehsilResponseDto>> getTehsilById(
            @PathVariable Long id) {

        TehsilResponseDto response =
                tehsilService.getTehsilById(id);

        return ResponseBuilder.success(
                response,
                "Tehsil fetched successfully.",
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TehsilResponseDto>>> getAllTehsils(
            Pageable pageable) {

        Page<TehsilResponseDto> response =
                tehsilService.getAllTehsils(pageable);

        return ResponseBuilder.success(
                response,
                "Tehsils fetched successfully.",
                HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<TehsilResponseDto>>> searchTehsils(
            @RequestBody TehsilSearchDto dto,
            Pageable pageable) {

        Page<TehsilResponseDto> response =
                tehsilService.searchTehsils(dto, pageable);

        return ResponseBuilder.success(
                response,
                "Tehsil search completed successfully.",
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TehsilResponseDto>> updateTehsil(
            @PathVariable Long id,
            @Valid @RequestBody TehsilRequestDto dto) {

        TehsilResponseDto response =
                tehsilService.updateTehsil(id, dto);

        return ResponseBuilder.success(
                response,
                "Tehsil updated successfully.",
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTehsil(
            @PathVariable Long id) {

        tehsilService.deleteTehsil(id);

        return ResponseBuilder.success(
                null,
                "Tehsil deleted successfully.",
                HttpStatus.OK);
    }

}
