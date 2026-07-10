package com.sms.dto;

import com.sms.enums.FacultySubjectStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacultySubjectSearchRequest {

	private Long facultyId;

	private Long subjectId;

	private String academicYear;

	private FacultySubjectStatus status;

}