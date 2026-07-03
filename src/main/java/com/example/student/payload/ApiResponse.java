package com.example.student.payload;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiResponse<T> {

    private boolean success;

    private String message;

    private int status;

    private T data;

    private LocalDateTime timestamp;
    
    private Map<String, String> errors;

    public ApiResponse() {

    }


    public ApiResponse(boolean success, String message, int status, T data, LocalDateTime timestamp,
			Map<String, String> errors) {
		super();
		this.success = success;
		this.message = message;
		this.status = status;
		this.data = data;
		this.timestamp = timestamp;
		this.errors = errors;
	}

	public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


	public Map<String, String> getErrors() {
		return errors;
	}


	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}