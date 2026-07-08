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

    private Integer semester;

    private LocalDate admissionDate;

    private AdmissionStatus status;

    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}