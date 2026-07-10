package com.sms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FacultySubjectRequestDto {


    @NotNull(message = "Faculty id is required")
    private Long facultyId;



    @NotNull(message = "Subject id is required")
    private Long subjectId;



    @NotNull(message = "Assigned date is required")
    @FutureOrPresent(message = "Assigned date cannot be past")
    private LocalDate assignedDate;



    @NotBlank(message = "Academic year is required")
    private String academicYear;


}