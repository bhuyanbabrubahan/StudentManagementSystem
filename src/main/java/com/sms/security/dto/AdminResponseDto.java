package com.sms.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AdminResponseDto {


    private Long id;

    private String email;

    private String role;

    private String message;


}