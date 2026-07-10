package com.sms.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SemesterRequestDto {


    @NotBlank(message = "Semester name is required")
    private String semesterName;


    @NotNull(message = "Semester number is required")
    @Min(value = 1, message = "Semester number must be greater than zero")
    private Integer semesterNumber;


    @NotNull(message = "Course id is required")
    private Long courseId;


}