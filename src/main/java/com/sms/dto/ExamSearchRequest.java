package com.sms.dto;

import java.time.LocalDate;

import com.sms.enums.ExamStatus;
import com.sms.enums.ExamType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamSearchRequest {

    private Long semesterId;

    private Long subjectId;

    private ExamType examType;

    private LocalDate examDate;

    private String academicYear;

    private ExamStatus status;

}