package com.sms.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sms.dto.FacultyRequestDto;
import com.sms.dto.FacultyResponseDto;
import com.sms.dto.FacultySearchRequest;
import com.sms.dto.PageResponse;
import com.sms.payload.ApiResponseDto;
import com.sms.service.FacultyService;
import com.sms.util.ResponseBuilder;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/faculties")
public class FacultyController {


    private final FacultyService facultyService;


    public FacultyController(
            FacultyService facultyService
    ) {

        this.facultyService = facultyService;

    }



    // ===============================
    // CREATE FACULTY
    // ===============================

    @PostMapping
    public ResponseEntity<ApiResponseDto<FacultyResponseDto>> createFaculty(
            @Valid @RequestBody FacultyRequestDto dto
    ) {


        FacultyResponseDto response =
                facultyService.createFaculty(dto);


        return ResponseBuilder.success(
                response,
                "Faculty Created Successfully",
                HttpStatus.CREATED
        );

    }





    // ===============================
    // GET FACULTY BY ID
    // Only Numeric ID Allowed
    // ===============================

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<ApiResponseDto<FacultyResponseDto>> getFacultyById(
            @PathVariable Long id
    ) {


        FacultyResponseDto response =
                facultyService.getFacultyById(id);


        return ResponseBuilder.success(
                response,
                "Faculty fetched successfully",
                HttpStatus.OK
        );

    }





    // ===============================
    // GET ALL FACULTIES
    // Pagination + Sorting
    // ===============================

    @GetMapping
    public ResponseEntity<ApiResponseDto<PageResponse<FacultyResponseDto>>> getAllFaculties(

            @RequestParam(defaultValue = "0")
            int page,


            @RequestParam(defaultValue = "10")
            int size,


            @RequestParam(defaultValue = "id")
            String sortBy,


            @RequestParam(defaultValue = "asc")
            String direction

    ) {


        PageResponse<FacultyResponseDto> response =

                facultyService.getAllFaculties(
                        page,
                        size,
                        sortBy,
                        direction
                );


        return ResponseBuilder.success(
                response,
                "Faculties fetched successfully",
                HttpStatus.OK
        );

    }







    // ===============================
    // UPDATE FACULTY
    // ===============================


    @PutMapping("/{id:\\d+}")
    public ResponseEntity<ApiResponseDto<FacultyResponseDto>> updateFaculty(

            @PathVariable Long id,


            @Valid
            @RequestBody FacultyRequestDto dto

    ) {



        FacultyResponseDto response =

                facultyService.updateFaculty(
                        id,
                        dto
                );



        return ResponseBuilder.success(
                response,
                "Faculty updated successfully",
                HttpStatus.OK
        );

    }







    // ===============================
    // DELETE FACULTY
    // Soft Delete
    // ===============================


    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<ApiResponseDto<Void>> deleteFaculty(

            @PathVariable Long id

    ) {


        facultyService.deleteFaculty(id);



        return ResponseBuilder.success(
                null,
                "Faculty deleted successfully",
                HttpStatus.OK
        );

    }







    // ===============================
    // SEARCH FACULTY
    // Dynamic Search
    // ===============================


    @PostMapping("/search")
    public ResponseEntity<ApiResponseDto<PageResponse<FacultyResponseDto>>> searchFaculties(


            @RequestBody FacultySearchRequest request,


            @RequestParam(defaultValue = "0")
            int page,


            @RequestParam(defaultValue = "10")
            int size,


            @RequestParam(defaultValue = "id")
            String sortBy,


            @RequestParam(defaultValue = "asc")
            String direction

    ) {



        PageResponse<FacultyResponseDto> response =

                facultyService.searchFaculties(
                        request,
                        page,
                        size,
                        sortBy,
                        direction
                );



        return ResponseBuilder.success(
                response,
                "Faculty search result",
                HttpStatus.OK
        );

    }


}