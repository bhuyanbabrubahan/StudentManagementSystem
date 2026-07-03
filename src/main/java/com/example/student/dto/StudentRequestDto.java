package com.example.student.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.student.entity.Gender;
import com.example.student.entity.StudentStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

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

    public StudentRequestDto() {

    }

    public StudentRequestDto(String rollNumber,
                             String firstName,
                             String lastName,
                             String email,
                             String phoneNumber,
                             Gender gender,
                             LocalDate dateOfBirth,
                             LocalDate admissionDate,
                             BigDecimal fees,
                             StudentStatus status,
                             String profileImage) {

        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.admissionDate = admissionDate;
        this.fees = fees;
        this.status = status;
        this.profileImage = profileImage;
    }

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(LocalDate admissionDate) {
		this.admissionDate = admissionDate;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	public StudentStatus getStatus() {
		return status;
	}

	public void setStatus(StudentStatus status) {
		this.status = status;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

    
}