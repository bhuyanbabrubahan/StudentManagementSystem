package com.sms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequestDto {

    //@NotBlank(message = "Subject code is required")
    private String subjectCode;

    @NotBlank(message = "Subject name is required")
    private String subjectName;

    @NotNull(message = "Credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 10, message = "Credits cannot exceed 10")
    private Integer credits;

    @NotNull(message = "Theory marks are required")
    @Min(value = 0, message = "Theory marks cannot be negative")
    @Max(value = 100, message = "Theory marks cannot exceed 100")
    private Integer theoryMarks;

    @NotNull(message = "Practical marks are required")
    @Min(value = 0, message = "Practical marks cannot be negative")
    @Max(value = 100, message = "Practical marks cannot exceed 100")
    private Integer practicalMarks;

    @NotNull(message = "Semester id is required")
    private Long semesterId;

}