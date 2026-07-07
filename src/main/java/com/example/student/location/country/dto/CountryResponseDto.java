package com.example.student.location.country.dto;

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
public class CountryResponseDto {

    private Long id;

    private String countryName;

    private String countryCode;

    private MasterStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
