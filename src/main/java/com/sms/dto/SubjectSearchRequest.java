package com.sms.dto;


import com.sms.enums.SubjectStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectSearchRequest {

    private String subjectCode;

    private String subjectName;

    private Integer credits;

    private Long semesterId;

    private SubjectStatus status;

}