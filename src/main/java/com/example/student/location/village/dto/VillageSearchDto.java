package com.example.student.location.village.dto;



import com.example.student.location.common.MasterStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VillageSearchDto {

    private String villageName;

    private String pincode;

    private Long tehsilId;

    private Long districtId;

    private Long stateId;

    private Long countryId;

    private MasterStatus status;

}