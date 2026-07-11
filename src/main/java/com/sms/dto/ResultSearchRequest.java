package com.sms.dto;

import java.math.BigDecimal;

import com.sms.enums.RecordStatus;
import com.sms.enums.ResultStatus;

import lombok.Data;

@Data
public class ResultSearchRequest {

    // Student Filters
    private Long studentId;
    private String rollNumber;
    private String studentName;

    // Exam Filter
    private Long examId;

    // Subject Filter
    private Long subjectId;

    // Semester Filter
    private Long semesterId;

    // Result Filters
    private ResultStatus resultStatus;
    private String grade;

    // Percentage Range
    private BigDecimal minPercentage;

    private BigDecimal maxPercentage;

    // Obtained Marks Range
    private Integer minObtainedMarks;
    private Integer maxObtainedMarks;

    // Record Status
    private RecordStatus recordStatus;
}