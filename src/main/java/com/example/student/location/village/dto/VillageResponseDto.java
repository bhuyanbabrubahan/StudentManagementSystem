package com.example.student.location.village.dto;


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
public class VillageResponseDto {

    private Long id;

    private String villageName;

    private String pincode;

    private Long tehsilId;

    private String tehsilName;

    private Long districtId;

    private String districtName;

    private Long stateId;

    private String stateName;

    private Long countryId;

    private String countryName;

    private MasterStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}