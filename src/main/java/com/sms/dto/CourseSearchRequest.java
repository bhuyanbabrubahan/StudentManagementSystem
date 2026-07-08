package com.sms.dto;

import java.math.BigDecimal;

import com.sms.entity.CourseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseSearchRequest {

    private String courseName;

    private String courseCode;

    private Long departmentId;

    private CourseStatus status;

    // optional filtering (future scalable)
    private BigDecimal minFees;

    private BigDecimal maxFees;
}