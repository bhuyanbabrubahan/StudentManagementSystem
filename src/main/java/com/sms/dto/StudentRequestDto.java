package com.sms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sms.entity.Gender;

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

	@NotBlank(message = "First Name is required")
	@Size(min = 2, max = 50,
	      message = "First Name should be between 2 and 50 characters")
	private String firstName;

	@NotBlank(message = "Last Name is required")
	@Size(min = 2, max = 50)
	private String lastName;

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

    private String profileImage;

    private Long departmentId;
    
    private Long villageId;
    
}