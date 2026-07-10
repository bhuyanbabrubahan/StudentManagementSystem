package com.sms.dto;


import java.time.LocalDate;
import java.time.LocalTime;

import com.sms.enums.ExamStatus;
import com.sms.enums.ExamType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ExamResponseDto {


    private Long id;


    // ==========================
    // Exam Information
    // ==========================

    private String examName;


    private ExamType examType;


    private LocalDate examDate;


    private LocalTime startTime;


    private LocalTime endTime;



    // ==========================
    // Marks
    // ==========================

    private Integer maximumMarks;


    private Integer passingMarks;



    // ==========================
    // Academic Year
    // ==========================

    private String academicYear;



    // ==========================
    // Subject
    // ==========================

    private Long subjectId;


    private String subjectName;



    // ==========================
    // Semester
    // ==========================

    private Long semesterId;


    private String semesterName;



    // ==========================
    // Status
    // ==========================

    private ExamStatus status;

}