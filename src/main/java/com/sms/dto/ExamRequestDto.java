package com.sms.dto;


import java.time.LocalDate;
import java.time.LocalTime;

import com.sms.enums.ExamType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamRequestDto {


    // ==========================
    // Semester
    // ==========================

    @NotNull(message = "Semester id is required")
    private Long semesterId;



    // ==========================
    // Subject
    // ==========================

    @NotNull(message = "Subject id is required")
    private Long subjectId;



    // ==========================
    // Exam Information
    // ==========================

    @NotNull(message = "Exam date is required")
    private LocalDate examDate;


    @NotNull(message = "Start time is required")
    private LocalTime startTime;


    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "Exam name is required")
    private String examName;

    @NotNull(message = "Exam type is required")
    private ExamType examType;

    @NotNull(message = "Academic year is required")
    private String academicYear;

    // ==========================
    // Marks
    // ==========================


    @NotNull(message = "Maximum marks is required")
    @Positive(message = "Maximum marks must be greater than zero")
    private Integer maximumMarks;



    @NotNull(message = "Passing marks is required")
    @PositiveOrZero(message = "Passing marks cannot be negative")
    private Integer passingMarks;


}