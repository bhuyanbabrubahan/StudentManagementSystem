package com.example.student.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.student.payload.ApiResponse;
import com.example.student.util.ResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateStudentException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateStudentException(DuplicateStudentException ex) {

        return ResponseBuilder.error(
                ex.getMessage(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return ResponseBuilder.error(
                ex.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Its for @valid exception
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error ->
                  errors.put(error.getField(),
                             error.getDefaultMessage()));

        ApiResponse<Object> response = new ApiResponse<>();

        response.setSuccess(false);
        response.setMessage("Validation Failed");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setData(null);
        response.setErrors(errors);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response,
                HttpStatus.BAD_REQUEST);
    }
    
    

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {

        return ResponseBuilder.error(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    @ExceptionHandler(DuplicateDepartmentException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateDepartmentException(DuplicateDepartmentException ex) {

        return ResponseBuilder.error(
                ex.getMessage(),
                HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException ex) {

        ApiResponse<Object> response = new ApiResponse<>();

        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}