package com.example.student.controller;

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

import com.example.student.dto.CourseRequestDto;
import com.example.student.dto.CourseResponseDto;
import com.example.student.dto.CourseSearchRequest;
import com.example.student.dto.PageResponse;
import com.example.student.payload.ApiResponse;
import com.example.student.service.CourseService;
import com.example.student.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponseDto>> createCourse(
            @Valid @RequestBody CourseRequestDto dto) {

        CourseResponseDto response = courseService.createCourse(dto);

        return ResponseBuilder.success(
                response,
                "Course created successfully",
                HttpStatus.CREATED
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>> getCourseById(
            @PathVariable Long id) {

        CourseResponseDto response = courseService.getCourseById(id);

        return ResponseBuilder.success(
                response,
                "Course fetched successfully",
                HttpStatus.OK
        );
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequestDto dto) {

        CourseResponseDto response = courseService.updateCourse(id, dto);

        return ResponseBuilder.success(
                response,
                "Course updated successfully",
                HttpStatus.OK
        );
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(
            @PathVariable Long id) {

        courseService.deleteCourse(id);

        return ResponseBuilder.success(
                null,
                "Course deleted successfully",
                HttpStatus.OK
        );
    }
    
    
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CourseResponseDto>>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        PageResponse<CourseResponseDto> response =
                courseService.getAllCourses(page, size, sortBy, direction);

        return ResponseBuilder.success(
                response,
                "Courses fetched successfully",
                HttpStatus.OK
        );
    }
    
    
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<CourseResponseDto>>> searchCourses(
            @RequestBody CourseSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        PageResponse<CourseResponseDto> response =
                courseService.searchCourses(request, page, size, sortBy, direction);

        return ResponseBuilder.success(
                response,
                "Courses fetched successfully",
                HttpStatus.OK
        );
    }
    
    
}