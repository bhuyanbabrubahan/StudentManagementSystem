package com.sms.dto;

import java.math.BigDecimal;

import com.sms.entity.Gender;
import com.sms.entity.StudentStatus;

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
