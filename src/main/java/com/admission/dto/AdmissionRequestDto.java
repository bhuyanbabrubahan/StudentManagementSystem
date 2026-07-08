package com.admission.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdmissionRequestDto {

	@NotNull(message = "Student Id is required")
	private Long studentId;

	@NotNull(message = "Department Id is required")
	private Long departmentId;

	@NotNull(message = "Course Id is required")
	private Long courseId;

	@NotBlank(message = "Academic year is required")
	private String academicYear;

	@NotNull(message = "Semester is required")
	@Min(value = 1, message = "Semester must be at least 1")
	@Max(value = 8, message = "Semester cannot be greater than 8")
	private Integer semester;

	
	private LocalDate admissionDate;

	private String remarks;

}