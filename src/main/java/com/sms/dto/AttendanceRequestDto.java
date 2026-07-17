package com.sms.dto;

import java.time.LocalDate;

import com.sms.enums.AttendanceStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "AttendanceRequestDto",
    description = "Request DTO used to mark student attendance for a faculty subject mapping."
)
public class AttendanceRequestDto {

    // ==========================
    // Student
    // ==========================

    @Schema(
        description = "Unique student ID",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Student id is required")
    private Long studentId;

    // ==========================
    // Faculty Subject Mapping
    // ==========================

    @Schema(
        description = "Faculty Subject Mapping ID",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Faculty subject mapping id is required.")
    private Long facultySubjectMappingId;

    // ==========================
    // Attendance Date
    // ==========================

    @Schema(
        description = "Date on which attendance is marked (yyyy-MM-dd)",
        example = "2026-07-10",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Attendance date is required.")
    private LocalDate attendanceDate;

    // ==========================
    // Attendance Status
    // ==========================

    @Schema(
        description = "Attendance status of the student",
        example = "PRESENT",
        allowableValues = {
            "PRESENT",
            "ABSENT",
            "LEAVE"
        },
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Attendance status is required")
    private AttendanceStatus attendanceStatus;

    // ==========================
    // Remarks
    // ==========================

    @Schema(
        description = "Optional remarks for attendance",
        example = "Student attended complete lecture."
    )
    @Size(
        max = 300,
        message = "Remarks cannot exceed 300 characters."
    )
    private String remarks;

}