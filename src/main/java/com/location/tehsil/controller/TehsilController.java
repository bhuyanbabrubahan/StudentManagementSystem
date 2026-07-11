package com.location.tehsil.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.location.tehsil.dto.TehsilRequestDto;
import com.location.tehsil.dto.TehsilResponseDto;
import com.location.tehsil.dto.TehsilSearchDto;
import com.location.tehsil.service.TehsilService;
import com.sms.payload.ApiResponseDto;
import com.sms.util.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tehsils")
@RequiredArgsConstructor
@Validated
public class TehsilController {

    private final TehsilService tehsilService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<TehsilResponseDto>> createTehsil(
            @Valid @RequestBody TehsilRequestDto dto) {

        TehsilResponseDto response =
                tehsilService.createTehsil(dto);

        return ResponseBuilder.success(
                response,
                "Tehsil created successfully.",
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<TehsilResponseDto>> getTehsilById(
            @PathVariable Long id) {

        TehsilResponseDto response =
                tehsilService.getTehsilById(id);

        return ResponseBuilder.success(
                response,
                "Tehsil fetched successfully.",
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<TehsilResponseDto>>> getAllTehsils(
            Pageable pageable) {

        Page<TehsilResponseDto> response =
                tehsilService.getAllTehsils(pageable);

        return ResponseBuilder.success(
                response,
                "Tehsils fetched successfully.",
                HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponseDto<Page<TehsilResponseDto>>> searchTehsils(
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
    public ResponseEntity<ApiResponseDto<TehsilResponseDto>> updateTehsil(
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
    public ResponseEntity<ApiResponseDto<Void>> deleteTehsil(
            @PathVariable Long id) {

        tehsilService.deleteTehsil(id);

        return ResponseBuilder.success(
                null,
                "Tehsil deleted successfully.",
                HttpStatus.OK);
    }

}
