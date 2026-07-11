package com.sms.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sms.dto.PageResponse;
import com.sms.dto.ResultRequestDto;
import com.sms.dto.ResultResponseDto;
import com.sms.dto.ResultSearchRequest;
import com.sms.payload.ApiResponseDto;
import com.sms.service.ResultService;
import com.sms.util.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
@Tag(
        name = "Result Management",
        description = "APIs for managing student examination results"
)
public class ResultController {



    private final ResultService resultService;



    // ==========================
    // Create Result
    // ==========================

    @Operation(
            summary = "Create Result",
            description = 
            "Creates a student result after validating student, exam and marks"
    )
    @ApiResponses({

    	@ApiResponse(
                responseCode = "200",
                description = "Result created successfully"
        ),

        @ApiResponse(
                responseCode = "400",
                description = "Business validation failed"
        ),

        @ApiResponse(
                responseCode = "404",
                description = "Student or Exam not found"
        )
    })
    @PostMapping
    public ResponseEntity<?> createResult(
            @RequestBody ResultRequestDto request
    ){

        ResultResponseDto response =
                resultService.createResult(request);

        return ResponseBuilder.success(
                response,
                "Result created successfully",
                HttpStatus.CREATED
        );

    }



    // ==========================
    // Update Result
    // ==========================

    @Operation(
            summary = "Update Result",
            description = "Updates existing student result"
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "200",
                    description = "Result updated successfully"
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid result data"
            )

    })
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ResultResponseDto>> updateResult(
            @PathVariable Long id,
            @Valid @RequestBody ResultRequestDto request
    ){

        ResultResponseDto response =
                resultService.updateResult(id, request);

        return ResponseBuilder.success(
                response,
                "Result updated successfully",
                HttpStatus.OK
        );

    }





    // ==========================
    // Get By Id
    // ==========================
    @Operation(
            summary = "Get Result By Id",
            description = "Fetch active result details using result id"
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "200",
                    description = "Result found"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Result not found"
            )

    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ResultResponseDto>> getById(
    		@PathVariable
    		@Parameter(
    		        description = "Result ID",
    		        example = "1"
    		)
    		Long id
    ){

        ResultResponseDto response =
                resultService.getResultById(id);

        return ResponseBuilder.success(
                response,
                "Result fetched successfully",
                HttpStatus.OK
        );

    }





    // ==========================
    // Pagination
    // ==========================
    @Operation(
            summary = "Get All Results",
            description =
            "Fetch paginated and sorted results"
    )
    @GetMapping
    public ResponseEntity<ApiResponseDto<PageResponse<ResultResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){

        PageResponse<ResultResponseDto> response =
                resultService.getAllResults(
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseBuilder.success(
                response,
                "Results fetched successfully",
                HttpStatus.OK
        );

    }





    // ==========================
    // Dynamic Search
    // ==========================
    @Operation(
            summary = "Search Results",
            description =
            "Dynamic search using student, exam, grade, status and marks"
    )
    @PostMapping("/search")
    public ResponseEntity<ApiResponseDto<PageResponse<ResultResponseDto>>> search(
            @Valid @RequestBody ResultSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){

        PageResponse<ResultResponseDto> response =
                resultService.searchResults(
                        request,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseBuilder.success(
                response,
                "Results searched successfully",
                HttpStatus.OK
        );

    }





    // ==========================
    // Soft Delete
    // ==========================
    @Operation(
            summary = "Delete Result",
            description = 
            "Soft deletes result by changing record status"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Result deleted successfully"
    )
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> delete(
            @PathVariable Long id
    ){

        resultService.deleteResult(id);

        return ResponseBuilder.success(
                null,
                "Result deleted successfully",
                HttpStatus.OK
        );

    }


}