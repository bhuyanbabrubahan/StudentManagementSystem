package com.example.student.location.district.dto;


import java.time.LocalDateTime;

import com.example.student.location.common.MasterStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictResponseDto {

    private Long id;

    private String districtName;

    private String districtCode;

    private Long stateId;

    private String stateName;

    private Long countryId;

    private String countryName;

    private MasterStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}