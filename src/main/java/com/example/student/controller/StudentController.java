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

import com.example.student.dto.PageResponse;
import com.example.student.dto.StudentRequestDto;
import com.example.student.dto.StudentResponseDto;
import com.example.student.dto.StudentSearchRequest;
import com.example.student.exception.GlobalExceptionHandler;
import com.example.student.payload.ApiResponse;
import com.example.student.service.StudentService;
import com.example.student.util.ResponseBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final GlobalExceptionHandler globalExceptionHandler;

    private final StudentService studentService;

    public StudentController(StudentService studentService, GlobalExceptionHandler globalExceptionHandler) {

        this.studentService = studentService;

        this.globalExceptionHandler = globalExceptionHandler;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDto>> saveStudent(@Valid @RequestBody StudentRequestDto requestDto){

        StudentResponseDto responseDto = studentService.saveStudent(requestDto);

        return ResponseBuilder.success(responseDto, "Student Created Successfully", HttpStatus.CREATED);

    }
    
    
    
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>>getStudentById(@PathVariable Long id){

        StudentResponseDto responseDto = studentService.getStudentById(id);

        return ResponseBuilder.success(responseDto, "Student fetched successfully", HttpStatus.OK);

    }
    
    
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudent(@PathVariable Long id,
    		@Valid @RequestBody StudentRequestDto dto) {
    	
    	StudentResponseDto response = studentService.updateStudent(id, dto);
    	
    	return ResponseBuilder.success(response, "Student updated successfully",
                HttpStatus.OK);
    }
    
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id){
    	
    	studentService.deleteStudent(id);
		return ResponseBuilder.success(null, "Student Deleted successfully ", HttpStatus.OK);
    	
    }
    
    
    
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<StudentResponseDto>>> getAllStudents(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String direction) {

        PageResponse<StudentResponseDto> response =
                studentService.getAllStudents(page, size, sortBy, direction);

        return ResponseBuilder.success(
                response,
                "Students fetched successfully",
                HttpStatus.OK
        );
    }
    
   
    
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<StudentResponseDto>>> searchStudents(
    		@RequestBody StudentSearchRequest request, 
    		@RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String direction){
    	
    		PageResponse<StudentResponseDto> response  = studentService.searchStudents(request, page, size, sortBy, direction);
    		
    		return ResponseBuilder.success(
    		        response,
    		        "Students fetched successfully",
    		        HttpStatus.OK

    		);
    }
    
    
    

}