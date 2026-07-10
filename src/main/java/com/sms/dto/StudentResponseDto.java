package com.sms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sms.entity.Gender;
import com.sms.enums.StudentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {

    private Long id;

    private String rollNumber;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Gender gender;

    private LocalDate dateOfBirth;

    private LocalDate admissionDate;

    private BigDecimal fees;

    private StudentStatus status;

    private String profileImage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    private Long departmentId;
    
    private String departmentName;
    
    private String village;

    private String tehsil;

    private String district;

    private String state;

    private String country;

    private String pincode;

    
    
}