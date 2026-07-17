package com.sms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.location.address.dto.AddressRequestDto;
import com.sms.entity.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
@Schema(
    name = "StudentRequestDto",
    description = "Request DTO used to create or update student information."
)
public class StudentRequestDto {
	
	
	@Schema(
	        description = "Student Email",
	        example = "rahul@gmail.com",
	        requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email")
	private String email;

	@Schema(
	        description = "Student Login Password",
	        example = "Rahul@123",
	        requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Password is required")
	@Pattern(
	        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$",
	        message = "Password must contain uppercase, lowercase, number and special character."
	)
	private String password;
	
	

    @Schema(
        description = "Student first name",
        example = "Rahul",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "First name is required")
    @Size(
        min = 2,
        max = 50,
        message = "First name must be between 2 and 50 characters"
    )
    private String firstName;
    
    

    @Schema(
        description = "Student last name",
        example = "Sharma",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Last name is required")
    @Size(
        min = 2,
        max = 50,
        message = "Last name must be between 2 and 50 characters"
    )
    private String lastName;
    
    

    @Schema(
        description = "Student mobile number",
        example = "9876543210",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Enter a valid 10 digit mobile number"
    )
    private String phoneNumber;
    
    

    @Schema(
        description = "Student gender",
        example = "MALE",
        allowableValues = {
            "MALE",
            "FEMALE",
            "OTHER"
        },
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Gender is required")
    private Gender gender;
    
    

    @Schema(
        description = "Student date of birth",
        example = "2004-05-20",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    
    

    @Schema(
        description = "Admission date",
        example = "2026-07-15",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Admission date is required")
    private LocalDate admissionDate;
    

    @Schema(
        description = "Admission fees",
        example = "45000.00",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Fees is required")
    @Positive(message = "Fees must be greater than zero")
    private BigDecimal fees;
    

    @Schema(
        description = "Student profile image",
        example = "images/student1.jpg"
    )
    private String profileImage;
    
    
    

    @Schema(
        description = "Department ID",
        example = "2",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Department is required")
    private Long departmentId;
    

    @Schema(
        description = "Student address details",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Address is required")
    @Valid
    private AddressRequestDto address;

}