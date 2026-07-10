package com.sms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sms.entity.Designation;
import com.sms.entity.Gender;
import com.sms.entity.Qualification;
import com.sms.enums.FacultyStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacultyResponseDto {

	private Long id;

	private Long userId;

	private String employeeCode;

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private Gender gender;

	private LocalDate dateOfBirth;

	private LocalDate joiningDate;

	private Designation designation;

	private Qualification qualification;

	private BigDecimal salary;

	private String profileImage;

	private FacultyStatus status;

	private Long departmentId;

	private String departmentName;

	private Long addressId;

	private String villageName;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}