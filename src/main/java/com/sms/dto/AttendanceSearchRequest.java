package com.sms.dto;

import java.time.LocalDate;

import com.sms.enums.AttendanceRecordStatus;
import com.sms.enums.AttendanceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceSearchRequest {

    private Long studentId;

    // Faculty filter
    private Long facultyId;

    // Subject filter
    private Long subjectId;

    // Direct mapping search
    private Long facultySubjectMappingId;

    private LocalDate attendanceDate;

    private AttendanceStatus attendanceStatus;

    private AttendanceRecordStatus status;

}