package com.sms.security.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.location.address.enums.AddressType;
import com.sms.entity.Designation;
import com.sms.entity.Gender;
import com.sms.entity.Qualification;
import com.sms.enums.RecordStatus;
import com.sms.security.entity.AdminDesignation;
import com.sms.security.entity.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProfileResponseDto", description = "Response DTO containing logged-in user profile details for Student, Faculty and Admin.")
public class ProfileResponseDto {

	// ==========================================================
	// USER INFORMATION (COMMON)
	// ==========================================================

	@Schema(description = "User Id", example = "34")
	private Long userId;

	@Schema(description = "Login Email", example = "rahul@gmail.com")
	private String email;

	@Schema(description = "User Role", example = "STUDENT")
	private Role role;

	// ==========================================================
	// STUDENT INFORMATION
	// ==========================================================

	private Long studentId;

	private String rollNumber;

	private String firstName;

	private String lastName;

	private String fullName;

	private String phoneNumber;

	private Gender gender;

	private LocalDate dateOfBirth;

	private LocalDate admissionDate;

	private BigDecimal fees;

	private String profileImage;

	private RecordStatus status;
	
	// ==========================================================
	// ADMIN INFORMATION
	// ==========================================================
	
	private Long adminId;

	private String adminName;

	private AdminDesignation adminDesignation;

	// ==========================================================
	// FACULTY INFORMATION
	// ==========================================================

	private Long facultyId;

	private String employeeCode;

	private Designation designation;

	private Qualification qualification;

	private LocalDate joiningDate;

	private BigDecimal salary;

	// ==========================================================
	// DEPARTMENT INFORMATION
	// ==========================================================

	private Long departmentId;

	private String departmentName;

	// ==========================================================
	// ADDRESS INFORMATION
	// ==========================================================

	private Long addressId;

	private String houseNumber;

	private String street;

	private String landmark;

	private AddressType addressType;

	// ==========================================================
	// LOCATION HIERARCHY
	// ==========================================================

	private String villageName;

	private String pincode;

	private String tehsilName;

	private String districtName;

	private String stateName;

	private String countryName;

}