package com.example.student.location.state.dto;

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
public class StateResponseDto {

    private Long id;

    private String stateName;

    private String stateCode;

    private Long countryId;

    private String countryName;

    private MasterStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
