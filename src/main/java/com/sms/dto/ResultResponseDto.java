package com.sms.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sms.enums.ResultStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResultResponseDto {

	@Schema(
	        description = "Result ID",
	        example = "10"
	)
	private Long id;

    // Student Details
    private Long studentId;
    private String rollNumber;
    
    @Schema(
            description = "Full Name of Student",
            example = "Rahul Sharma"
    )
    private String studentName;

    // Exam Details
    private Long examId;
    private String examName;

    // Subject Details
    private Long subjectId;
    private String subjectName;

    // Semester Details
    private Long semesterId;
    private String semesterName;

    // Marks
    private Integer obtainedMarks;
    private Integer maximumMarks;

    // Calculated Fields
    @Schema(
            description = "Calculated percentage",
            example = "87.50"
    )
    private BigDecimal percentage;
    
    
    @Schema(
            description = "Calculated Grade",
            example = "A"
    )
    private String grade;
    
    
    @Schema(
            description = "PASS or FAIL"
    )
    private ResultStatus resultStatus;
    
    
    // Remarks
    private String remarks;

    // Audit Fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}