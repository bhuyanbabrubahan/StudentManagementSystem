package com.example.student.location.district.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.location.district.dto.DistrictRequestDto;
import com.example.student.location.district.dto.DistrictResponseDto;
import com.example.student.location.district.dto.DistrictSearchDto;
import com.example.student.location.district.service.DistrictService;
import com.example.student.payload.ApiResponse;
import com.example.student.util.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @PostMapping
    public ResponseEntity<ApiResponse<DistrictResponseDto>> createDistrict(
            @Valid @RequestBody DistrictRequestDto dto) {

        DistrictResponseDto response = districtService.createDistrict(dto);

        return ResponseBuilder.success(
                response,
                "District created successfully.",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DistrictResponseDto>> getDistrictById(
            @PathVariable Long id) {

        DistrictResponseDto response = districtService.getDistrictById(id);

        return ResponseBuilder.success(
                response,
                "District fetched successfully.",
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DistrictResponseDto>>> getAllDistricts(
            Pageable pageable) {

        Page<DistrictResponseDto> response =
                districtService.getAllDistricts(pageable);

        return ResponseBuilder.success(
                response,
                "District list fetched successfully.",
                HttpStatus.OK
        );
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<DistrictResponseDto>>> searchDistricts(
            @RequestBody DistrictSearchDto dto,
            Pageable pageable) {

        Page<DistrictResponseDto> response =
                districtService.searchDistricts(dto, pageable);

        return ResponseBuilder.success(
                response,
                "District search completed successfully.",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DistrictResponseDto>> updateDistrict(
            @PathVariable Long id,
            @Valid @RequestBody DistrictRequestDto dto) {

        DistrictResponseDto response =
                districtService.updateDistrict(id, dto);

        return ResponseBuilder.success(
                response,
                "District updated successfully.",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteDistrict(
            @PathVariable Long id) {

        districtService.deleteDistrict(id);

        return ResponseBuilder.success(
                null,
                "District deleted successfully.",
                HttpStatus.OK
        );
    }

}