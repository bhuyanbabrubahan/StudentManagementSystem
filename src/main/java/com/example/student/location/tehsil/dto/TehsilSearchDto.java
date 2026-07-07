package com.example.student.location.tehsil.dto;

import com.example.student.location.common.MasterStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TehsilSearchDto {

    private String tehsilName;

    private Long districtId;

    private Long stateId;

    private Long countryId;

    private MasterStatus status;

}
