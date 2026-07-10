package com.sms.dto;


import java.time.LocalDate;

import com.sms.enums.AttendanceRecordStatus;
import com.sms.enums.AttendanceStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AttendanceResponseDto {


    private Long id;



    // ==========================
    // Student Details
    // ==========================

    private Long studentId;

    private String studentName;



    // ==========================
    // Faculty Subject Details
    // ==========================

    private Long facultySubjectMappingId;

    private String facultyName;

    private String subjectName;



    // ==========================
    // Attendance
    // ==========================

    private LocalDate attendanceDate;

    private AttendanceStatus attendanceStatus;


    private String remarks;



    // ==========================
    // Record Status
    // ==========================

    private AttendanceRecordStatus status;


}