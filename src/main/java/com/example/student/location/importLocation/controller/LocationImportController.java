package com.example.student.location.importLocation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.location.importLocation.dto.ImportResultDto;
import com.example.student.location.importLocation.service.LocationImportService;
import com.example.student.payload.ApiResponse;
import com.example.student.util.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/location-import")
@RequiredArgsConstructor
public class LocationImportController {

    private final LocationImportService locationImportService;

    @PostMapping("/import")
    public ResponseEntity<ApiResponse<ImportResultDto>> importLocation() {

        ImportResultDto response =
                locationImportService.importLocationMaster();

        return ResponseBuilder.success(
                response,
                "Location Master Imported Successfully",
                HttpStatus.OK
        );
    }

}