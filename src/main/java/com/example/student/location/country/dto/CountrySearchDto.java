package com.example.student.location.country.dto;

import com.example.student.location.common.MasterStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountrySearchDto {

    private String countryName;

    private String countryCode;

    private MasterStatus status;

}