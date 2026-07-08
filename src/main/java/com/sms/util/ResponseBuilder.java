package com.sms.util;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sms.payload.ApiResponse;

public class ResponseBuilder {

    private ResponseBuilder() {

    }

    public static <T> ResponseEntity<ApiResponse<T>> success(
            T data,
            String message,
            HttpStatus status) {

        ApiResponse<T> response = new ApiResponse<>();

        response.setSuccess(true);
        response.setMessage(message);
        response.setStatus(status.value());
        response.setData(data);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(
            String message,
            HttpStatus status) {

        ApiResponse<T> response = new ApiResponse<>();

        response.setSuccess(false);
        response.setMessage(message);
        response.setStatus(status.value());
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, status);
    }
}