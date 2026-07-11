package com.sms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResultRequestDto {

	@Schema(
	        description = "Student ID",
	        example = "1",
	        requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Student ID is required.")
	private Long studentId;

	
	
	
	@Schema(
	        description = "Exam ID",
	        example = "5",
	        requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Exam ID is required.")
	private Long examId;

	
	
	
	@Schema(
	        description = "Obtained marks",
	        example = "85",
	        minimum = "0",
	        requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Obtained marks are required.")
	@Min(value = 0, message = "Obtained marks cannot be negative.")
	private Integer obtainedMarks;

	
	
	
	@Schema(
	        description = "Teacher remarks",
	        example = "Excellent Performance"
	)
	@Size(max = 500, message = "Remarks cannot exceed 500 characters.")
	private String remarks;
}