package com.example.student.location.state.dto;

import com.example.student.location.common.MasterStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateSearchDto {

    private String stateName;

    private String stateCode;

    private Long countryId;

    private MasterStatus status;

}