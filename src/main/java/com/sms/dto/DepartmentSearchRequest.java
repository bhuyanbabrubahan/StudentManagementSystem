package com.sms.dto;

import com.sms.entity.DepartmentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSearchRequest {

	private String departmentName;
	private String departmentCode;
	private DepartmentStatus status;

}
