package com.example.student.location.country.mapper;

import org.springframework.stereotype.Component;

import com.example.student.location.common.MasterStatus;
import com.example.student.location.country.dto.CountryRequestDto;
import com.example.student.location.country.dto.CountryResponseDto;
import com.example.student.location.country.entity.Country;

@Component
public class CountryMapper {

    public Country toEntity(CountryRequestDto dto) {

        Country country = new Country();

        country.setCountryName(dto.getCountryName().trim());

        country.setCountryCode(dto.getCountryCode().trim().toUpperCase());

        country.setStatus(MasterStatus.ACTIVE);

        return country;
    }

    public CountryResponseDto toDto(Country entity) {

        CountryResponseDto dto = new CountryResponseDto();

        dto.setId(entity.getId());
        dto.setCountryName(entity.getCountryName());
        dto.setCountryCode(entity.getCountryCode());
        dto.setStatus(entity.getStatus());

        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public void updateEntity(Country entity,
                             CountryRequestDto dto) {

        entity.setCountryName(dto.getCountryName().trim());

        entity.setCountryCode(dto.getCountryCode().trim().toUpperCase());

    }

}