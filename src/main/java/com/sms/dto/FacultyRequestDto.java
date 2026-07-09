package com.sms.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

import com.location.address.dto.AddressRequestDto;
import com.sms.entity.Designation;
import com.sms.entity.Gender;
import com.sms.entity.Qualification;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FacultyRequestDto {


    @NotBlank(message = "First name is required")
    private String firstName;



    @NotBlank(message = "Last name is required")
    private String lastName;



    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Phone number must be 10 digits"
    )
    private String phoneNumber;



    @NotNull(message = "Gender is required")
    private Gender gender;



    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;



    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;



    @NotNull(message = "Designation is required")
    private Designation designation;



    @NotNull(message = "Qualification is required")
    private Qualification qualification;



    @NotNull(message = "Salary is required")
    @DecimalMin(
        value = "0.0",
        message = "Salary must be positive"
    )
    private BigDecimal salary;



    private String profileImage;



    @NotNull(message = "Department is required")
    private Long departmentId;




    @Valid
    @NotNull(message = "Address is required")
    private AddressRequestDto address;




    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;




    @NotBlank(message = "Password is required")
    private String password;


}