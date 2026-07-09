package com.sms.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class RegisterResponseDto {


	private Long studentId;

    private String rollNumber;

    private String email;

    private String message;

}