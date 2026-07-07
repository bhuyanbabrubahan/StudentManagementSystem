package com.example.student.location.village.controller;

import java.util.List;

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

import com.example.student.location.village.dto.VillageRequestDto;
import com.example.student.location.village.dto.VillageResponseDto;
import com.example.student.location.village.dto.VillageSearchDto;
import com.example.student.location.village.service.VillageService;
import com.example.student.payload.ApiResponse;
import com.example.student.util.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/villages")
@RequiredArgsConstructor
public class VillageController {

    private final VillageService villageService;

    @PostMapping
    public ResponseEntity<ApiResponse<VillageResponseDto>> createVillage(
            @Valid @RequestBody VillageRequestDto dto) {

        VillageResponseDto response =
                villageService.createVillage(dto);

        return ResponseBuilder.success(
                response,
                "Village created successfully.",
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VillageResponseDto>> getVillage(
            @PathVariable Long id) {

        VillageResponseDto response =
                villageService.getVillageById(id);

        return ResponseBuilder.success(
                response,
                "Village fetched successfully.",
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<VillageResponseDto>>> getAllVillages(
            Pageable pageable) {

        Page<VillageResponseDto> response =
                villageService.getAllVillages(pageable);

        return ResponseBuilder.success(
                response,
                "Villages fetched successfully.",
                HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<VillageResponseDto>>> searchVillages(
            @RequestBody VillageSearchDto dto,
            Pageable pageable) {

        Page<VillageResponseDto> response =
                villageService.searchVillages(dto, pageable);

        return ResponseBuilder.success(
                response,
                "Village search completed successfully.",
                HttpStatus.OK);
    }

    @GetMapping("/tehsil/{tehsilId}")
    public ResponseEntity<ApiResponse<List<VillageResponseDto>>> getVillagesByTehsil(
            @PathVariable Long tehsilId) {

        List<VillageResponseDto> response =
                villageService.getVillagesByTehsil(tehsilId);

        return ResponseBuilder.success(
                response,
                "Villages fetched successfully.",
                HttpStatus.OK);
    }

    @GetMapping("/pincode/{pincode}")
    public ResponseEntity<ApiResponse<List<VillageResponseDto>>> getVillagesByPincode(
            @PathVariable String pincode) {

        List<VillageResponseDto> response =
                villageService.getVillagesByPincode(pincode);

        return ResponseBuilder.success(
                response,
                "Villages fetched successfully.",
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VillageResponseDto>> updateVillage(
            @PathVariable Long id,
            @Valid @RequestBody VillageRequestDto dto) {

        VillageResponseDto response =
                villageService.updateVillage(id, dto);

        return ResponseBuilder.success(
                response,
                "Village updated successfully.",
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVillage(
            @PathVariable Long id) {

        villageService.deleteVillage(id);

        return ResponseBuilder.success(
                null,
                "Village deleted successfully.",
                HttpStatus.OK);
    }

}
