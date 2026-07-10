package com.sms.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacultySubjectResponseDto {

	private Long id;

	private Long facultyId;

	private String facultyName;

	private Long subjectId;

	private String subjectName;

	private LocalDate assignedDate;

	private String academicYear;

	private String status;

}