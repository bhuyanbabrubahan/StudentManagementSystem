package com.sms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.location.address.enums.AddressType;
import com.sms.entity.Gender;
import com.sms.enums.RecordStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "StudentResponseDto",
    description = "Response DTO containing complete student information."
)
public class StudentResponseDto {

    @Schema(
        description = "Unique student ID",
        example = "1"
    )
    private Long id;

    @Schema(
        description = "System generated roll number",
        example = "STU20260001"
    )
    private String rollNumber;

    @Schema(
        description = "Student first name",
        example = "Rahul"
    )
    private String firstName;

    @Schema(
        description = "Student last name",
        example = "Sharma"
    )
    private String lastName;

    @Schema(
        description = "Student full name",
        example = "Rahul Sharma"
    )
    private String fullName;

    @Schema(
        description = "Student mobile number",
        example = "9876543210"
    )
    private String phoneNumber;

    @Schema(
        description = "Gender",
        example = "MALE"
    )
    private Gender gender;

    @Schema(
        description = "Date of birth",
        example = "2004-05-20"
    )
    private LocalDate dateOfBirth;

    @Schema(
        description = "Admission date",
        example = "2026-07-15"
    )
    private LocalDate admissionDate;

    @Schema(
        description = "Student fees",
        example = "45000.00"
    )
    private BigDecimal fees;

    @Schema(
        description = "Profile image",
        example = "images/student1.jpg"
    )
    private String profileImage;

    @Schema(
        description = "Current record status",
        example = "ACTIVE"
    )
    private RecordStatus status;

    // =========================
    // Department
    // =========================

    @Schema(
        description = "Department ID",
        example = "2"
    )
    private Long departmentId;

    @Schema(
        description = "Department name",
        example = "Computer Science"
    )
    private String departmentName;

    // =========================
    // Address
    // =========================

    @Schema(
        description = "House Number",
        example = "12A"
    )
    private String houseNumber;

    @Schema(
        description = "Street",
        example = "University Road"
    )
    private String street;

    @Schema(
        description = "Landmark",
        example = "Near SBI Bank"
    )
    private String landmark;

    @Schema(
        description = "Address Type",
        example = "PERMANENT"
    )
    private AddressType addressType;

    @Schema(
        description = "Village",
        example = "Gangadahani"
    )
    private String village;

    @Schema(
        description = "Tehsil",
        example = "Aska"
    )
    private String tehsil;

    @Schema(
        description = "District",
        example = "Ganjam"
    )
    private String district;

    @Schema(
        description = "State",
        example = "Odisha"
    )
    private String state;

    @Schema(
        description = "Country",
        example = "India"
    )
    private String country;

    @Schema(
        description = "Postal Code",
        example = "761018"
    )
    private String pincode;

    // =========================
    // Audit
    // =========================

    @Schema(
        description = "Created At",
        example = "2026-07-15T10:30:45"
    )
    private LocalDateTime createdAt;

    @Schema(
        description = "Updated At",
        example = "2026-07-15T15:20:10"
    )
    private LocalDateTime updatedAt;

}