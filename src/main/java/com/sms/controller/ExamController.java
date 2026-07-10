package com.sms.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.sms.dto.ExamRequestDto;
import com.sms.dto.ExamResponseDto;
import com.sms.dto.PageResponse;
import com.sms.dto.ExamSearchRequest;
import com.sms.payload.ApiResponse;
import com.sms.service.ExamService;
import com.sms.util.ResponseBuilder;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {



    private final ExamService service;



    // ==========================
    // CREATE
    // ==========================


    @PostMapping
    public ResponseEntity<ApiResponse<ExamResponseDto>> createExam(
            
            @Valid
            @RequestBody ExamRequestDto dto
            
    ){

        ExamResponseDto response =
                service.createExam(dto);


        return ResponseBuilder.success(
                response,
                "Exam created successfully",
                HttpStatus.CREATED
        );

    }





    // ==========================
    // GET BY ID
    // ==========================


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamResponseDto>> getById(
            @PathVariable Long id
    ){

        return ResponseBuilder.success(
                service.getExamById(id),
                "Exam fetched successfully",
                HttpStatus.OK
        );

    }





    // ==========================
    // GET ALL
    // ==========================


    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ExamResponseDto>>> getAll(

            @RequestParam(defaultValue="0")
            int page,

            @RequestParam(defaultValue="10")
            int size,

            @RequestParam(defaultValue="id")
            String sortBy,

            @RequestParam(defaultValue="asc")
            String direction

    ){


        return ResponseBuilder.success(

                service.getAllExams(
                        page,
                        size,
                        sortBy,
                        direction
                ),

                "Exam fetched successfully",

                HttpStatus.OK
        );

    }





    // ==========================
    // UPDATE
    // ==========================


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamResponseDto>> update(

            @PathVariable Long id,

            @Valid
            @RequestBody ExamRequestDto dto

    ){


        return ResponseBuilder.success(

                service.updateExam(
                        id,
                        dto
                ),

                "Exam updated successfully",

                HttpStatus.OK
        );

    }





    // ==========================
    // DELETE
    // ==========================


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(

            @PathVariable Long id

    ){


        service.deleteExam(id);


        return ResponseBuilder.success(
                null,
                "Exam deleted successfully",
                HttpStatus.OK
        );

    }




    // ==========================
    // SEARCH
    // ==========================


    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<ExamResponseDto>>> search(

            @RequestBody ExamSearchRequest request,

            @RequestParam(defaultValue="0")
            int page,

            @RequestParam(defaultValue="10")
            int size,

            @RequestParam(defaultValue="id")
            String sortBy,

            @RequestParam(defaultValue="asc")
            String direction

    ){


        return ResponseBuilder.success(

                service.searchExams(
                        request,
                        page,
                        size,
                        sortBy,
                        direction
                ),

                "Exam searched successfully",

                HttpStatus.OK
        );


    }


}