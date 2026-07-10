package com.sms.dto;


import com.sms.enums.SemesterStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SemesterSearchRequest {


    private String semesterName;


    private Integer semesterNumber;


    private Long courseId;


    private SemesterStatus status;

}