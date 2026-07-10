package com.sms.dto;


import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SemesterRequestDto {

    @NotBlank(message = "Semester name is required")
    @Size(max = 50, message = "Semester name cannot exceed 50 characters")
    private String semesterName;

    @NotNull(message = "Semester number is required")
    @Min(value = 1, message = "Semester number must be at least 1")
    @Max(value = 20, message = "Semester number cannot exceed 20")
    private Integer semesterNumber;

    @NotNull(message = "Semester start date is required")
    private LocalDate semesterStartDate;

    @NotNull(message = "Semester end date is required")
    private LocalDate semesterEndDate;

    @NotNull(message = "Total working days is required")
    @Min(value = 1, message = "Total working days must be greater than 0")
    @Max(value = 366, message = "Total working days cannot exceed 366")
    private Integer totalWorkingDays;

    @NotNull(message = "Course id is required")
    @Positive(message = "Course id must be greater than zero")
    private Long courseId;

}