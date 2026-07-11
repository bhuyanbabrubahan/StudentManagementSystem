package com.sms.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sms.payload.ApiResponseDto;
import com.sms.util.ResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateStudentException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleDuplicateStudentException(DuplicateStudentException ex) {

        return ResponseBuilder.error(
                ex.getMessage(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return ResponseBuilder.error(
                ex.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Its for @valid exception
    public ResponseEntity<ApiResponseDto<Object>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error ->
                  errors.putIfAbsent(error.getField(),
                             error.getDefaultMessage()));

        ApiResponseDto<Object> response = new ApiResponseDto<>();

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
    public ResponseEntity<ApiResponseDto<Object>> handleException(Exception ex) {

        return ResponseBuilder.error(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    @ExceptionHandler(DuplicateDepartmentException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleDuplicateDepartmentException(DuplicateDepartmentException ex) {

        return ResponseBuilder.error(
                ex.getMessage(),
                HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleBusinessException(BusinessException ex) {

        ApiResponseDto<Object> response = new ApiResponseDto<>();

        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleJsonParseException(
            HttpMessageNotReadableException ex
    ) {

        return ResponseBuilder.error(
                "Invalid request format or unknown field provided.",
                HttpStatus.BAD_REQUEST
        );

    }

}