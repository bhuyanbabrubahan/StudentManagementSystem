package com.sms.dto;

import com.sms.entity.Designation;
import com.sms.entity.Qualification;
import com.sms.enums.FacultyStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacultySearchRequest {
	
	private Long id;

	private String firstName;

	private String lastName;

	private String employeeCode;

	private Long departmentId;

	private Designation designation;

	private Qualification qualification;

	private FacultyStatus status;

}