package com.sms.dto;

import java.math.BigDecimal;

import com.sms.entity.CourseStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {

    @NotBlank(message = "Course code is required")
    @Size(max = 20)
    private String courseCode;

    @NotBlank(message = "Course name is required")
    @Size(max = 100)
    private String courseName;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 month")
    private Integer durationInMonths;

    @NotNull(message = "Fees is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal fees;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Department is required")
    private Long departmentId;

    private CourseStatus status;
}