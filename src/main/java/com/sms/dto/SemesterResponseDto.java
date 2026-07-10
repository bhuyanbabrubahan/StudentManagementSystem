package com.sms.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SemesterResponseDto {

	private Long id;

	private String semesterName;

	private Integer semesterNumber;

	private LocalDate semesterStartDate;

	private LocalDate semesterEndDate;

	private Integer totalWorkingDays;

	private Long courseId;

	private String courseName;

	private String status;

}