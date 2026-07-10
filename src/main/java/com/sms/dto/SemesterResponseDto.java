package com.sms.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SemesterResponseDto {


    private Long id;


    private String semesterName;


    private Integer semesterNumber;


    private Long courseId;


    private String courseName;


    private String status;

}