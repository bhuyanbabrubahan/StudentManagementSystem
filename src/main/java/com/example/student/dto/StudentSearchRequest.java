package com.example.student.dto;

import java.math.BigDecimal;

import com.example.student.entity.Gender;
import com.example.student.entity.StudentStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentSearchRequest {

	private StudentStatus status;

    private Gender gender;

    private BigDecimal fees;

    
    
}
