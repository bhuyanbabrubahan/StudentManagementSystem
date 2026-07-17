package com.sms.dto;

import java.time.LocalDate;

import com.sms.enums.AttendanceStatus;
import com.sms.enums.RecordStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "AttendanceSearchRequest",
    description = "Request DTO used to search attendance records using optional search filters."
)
public class AttendanceSearchRequest {

    @Schema(
        description = "Search attendance by Student ID",
        example = "101"
    )
    private Long studentId;

    @Schema(
        description = "Search attendance by Faculty ID",
        example = "10"
    )
    private Long facultyId;

    @Schema(
        description = "Search attendance by Subject ID",
        example = "5"
    )
    private Long subjectId;

    @Schema(
        description = "Search attendance by Faculty Subject Mapping ID",
        example = "15"
    )
    private Long facultySubjectMappingId;

    @Schema(
        description = "Search attendance by attendance date (yyyy-MM-dd)",
        example = "2026-07-10"
    )
    private LocalDate attendanceDate;

    @Schema(
        description = "Search attendance by attendance status",
        example = "PRESENT",
        allowableValues = {
            "PRESENT",
            "ABSENT",
            "LEAVE"
        }
    )
    private AttendanceStatus attendanceStatus;

    @Schema(
        description = "Search by record status. By default ACTIVE records are returned.",
        example = "ACTIVE",
        allowableValues = {
            "ACTIVE",
            "INACTIVE",
            "DELETED"
        }
    )
    private RecordStatus status;

}