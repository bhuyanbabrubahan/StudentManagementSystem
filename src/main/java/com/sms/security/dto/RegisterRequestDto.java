package com.sms.security.dto;


import java.time.LocalDate;

import com.location.address.dto.AddressRequestDto;
import com.sms.entity.Gender;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterRequestDto {


    @NotBlank(message = "First name is required")
    private String firstName;


    @NotBlank(message = "Last name is required")
    private String lastName;



    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;



    @NotBlank(message = "Password is required")
    private String password;



    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp="^[0-9]{10}$",
            message="Phone number must be 10 digits"
    )
    private String phoneNumber;



    @NotNull(message="Gender is required")
    private Gender gender;



    @Past(message="Date of birth must be in past")
    private LocalDate dateOfBirth;



    @NotNull(message = "Department is required")
    private Long departmentId;



    @Valid
    @NotNull(message = "Address is required")
    private AddressRequestDto address;


}