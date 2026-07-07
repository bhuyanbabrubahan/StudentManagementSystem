package com.example.student.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.student.entity.Gender;
import com.example.student.entity.StudentStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {

	@NotBlank(message = "Roll Number is required")
	private String rollNumber;

	@NotBlank(message = "First Name is required")
	@Size(min = 2, max = 50,
	      message = "First Name should be between 2 and 50 characters")
	private String firstName;

	@NotBlank(message = "Last Name is required")
	@Size(min = 2, max = 50)
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Enter valid email")
	private String email;

	@NotBlank(message = "Phone Number is required")
	@Pattern(
	    regexp = "^[6-9]\\d{9}$",
	    message = "Enter valid 10 digit mobile number"
	)
	private String phoneNumber;

	@NotNull(message = "Gender is required")
	private Gender gender;

	@NotNull(message = "Date Of Birth is required")
	private LocalDate dateOfBirth;

	@NotNull(message = "Admission Date is required")
	private LocalDate admissionDate;

	@NotNull(message = "Fees is required")
	@Positive(message = "Fees must be greater than zero")
	private BigDecimal fees;

	@NotNull(message = "Status is required")
	private StudentStatus status;

    private String profileImage;

    private Long departmentId;
    
    private Long villageId;
    
}