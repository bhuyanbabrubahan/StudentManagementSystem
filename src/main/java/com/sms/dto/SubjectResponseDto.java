package com.sms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectResponseDto {

    private Long id;

    private String subjectCode;

    private String subjectName;

    private Integer credits;

    private Integer theoryMarks;

    private Integer practicalMarks;

    private Long semesterId;

    private String semesterName;

    private String status;

}