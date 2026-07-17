package com.sms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sms.enums.AttendanceStatus;
import com.sms.enums.RecordStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "AttendanceResponseDto",
    description = "Response DTO containing attendance details of a student."
)
public class AttendanceResponseDto {

    @Schema(
        description = "Unique attendance record ID",
        example = "1"
    )
    private Long id;

    // ==========================
    // Student Details
    // ==========================

    @Schema(
        description = "Student ID",
        example = "101"
    )
    private Long studentId;

    @Schema(
        description = "Student full name",
        example = "Rahul Kumar"
    )
    private String studentName;

    // ==========================
    // Faculty Subject Details
    // ==========================

    @Schema(
        description = "Faculty Subject Mapping ID",
        example = "10"
    )
    private Long facultySubjectMappingId;

    @Schema(
        description = "Faculty name",
        example = "Dr. Amit Sharma"
    )
    private String facultyName;

    @Schema(
        description = "Subject name",
        example = "Data Structures"
    )
    private String subjectName;

    // ==========================
    // Attendance Details
    // ==========================

    @Schema(
        description = "Attendance date",
        example = "2026-07-10"
    )
    private LocalDate attendanceDate;

    @Schema(
        description = "Attendance status",
        example = "PRESENT"
    )
    private AttendanceStatus attendanceStatus;

    @Schema(
        description = "Remarks added while marking attendance",
        example = "Student attended complete lecture."
    )
    private String remarks;

    // ==========================
    // Record Status
    // ==========================

    @Schema(
        description = "Current record status",
        example = "ACTIVE"
    )
    private RecordStatus status;
    
    
	@Schema(
			description = "Record creation timestamp", 
			example = "2026-07-10T09:15:30"
	)
	private LocalDateTime createdAt;
	

	@Schema(
			description = "Record last updated timestamp", 
			example = "2026-07-10T11:45:20"
	)
	private LocalDateTime updatedAt;

}