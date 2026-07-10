package com.sms.dto;


import java.time.LocalDate;

import com.sms.enums.AttendanceStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AttendanceRequestDto {


    // ==========================
    // Student
    // ==========================

    @NotNull(message = "Student id is required")
    private Long studentId;



    // ==========================
    // Faculty Subject Mapping
    // ==========================

    @NotNull(message = "Faculty subject mapping id is required")
    private Long facultySubjectMappingId;



    // ==========================
    // Attendance Date
    // ==========================

    @NotNull(message = "Attendance date is required")
    private LocalDate attendanceDate;



    // ==========================
    // Attendance Status
    // ==========================

    @NotNull(message = "Attendance status is required")
    private AttendanceStatus attendanceStatus;



    // ==========================
    // Remarks
    // ==========================

    @Size(
        max = 300,
        message = "Remarks cannot exceed 300 characters"
    )
    private String remarks;

}