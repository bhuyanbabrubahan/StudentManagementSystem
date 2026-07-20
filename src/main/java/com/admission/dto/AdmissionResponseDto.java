package com.admission.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.admission.entity.AdmissionStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdmissionResponseDto {

    private Long id;

    private String admissionNumber;

    private Long studentId;
    private String studentName;
    private String studentRollNumber;

    private Long departmentId;
    private String departmentName;

    private Long courseId;
    private String courseName;

    private String academicYear;

    private Long semesterId;

    private String semesterName;

    private LocalDate admissionDate;

    private AdmissionStatus admissionStatus;

    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}