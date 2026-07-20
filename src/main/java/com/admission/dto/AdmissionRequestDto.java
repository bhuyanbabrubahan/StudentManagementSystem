package com.admission.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

	@Pattern(
			regexp="^\\d{4}-\\d{4}$",
			message="Academic year must be like 2026-2027"
			)
	@NotBlank(message = "Academic year is required")
	private String academicYear;

	@NotNull(message = "Semester is required")
	@Min(value = 1, message = "Semester must be at least 1")
	@Max(value = 8, message = "Semester cannot be greater than 8")
	private Long semesterId;
	
	
	
	@NotNull(message = "Admission date is required")
	private LocalDate admissionDate;

	@Size(max = 500)
	private String remarks;

}