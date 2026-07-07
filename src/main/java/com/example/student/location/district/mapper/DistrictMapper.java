package com.example.student.location.district.mapper;


import org.springframework.stereotype.Component;

import com.example.student.location.common.MasterStatus;
import com.example.student.location.common.util.LocationUtils;
import com.example.student.location.district.dto.DistrictRequestDto;
import com.example.student.location.district.dto.DistrictResponseDto;
import com.example.student.location.district.entity.District;
import com.example.student.location.state.entity.State;


@Component
public class DistrictMapper {

    public District toEntity(DistrictRequestDto dto,
                             State state) {

        District district = new District();

        district.setDistrictName(
                LocationUtils.normalizeName(dto.getDistrictName()));

        district.setDistrictCode(
                LocationUtils.normalizeCode(dto.getDistrictCode()));

        district.setState(state);

        district.setStatus(MasterStatus.ACTIVE);

        return district;
    }

    public DistrictResponseDto toDto(District entity) {

        DistrictResponseDto dto = new DistrictResponseDto();

        dto.setId(entity.getId());

        dto.setDistrictName(entity.getDistrictName());

        dto.setDistrictCode(entity.getDistrictCode());

        dto.setStatus(entity.getStatus());

        dto.setStateId(entity.getState().getId());

        dto.setStateName(entity.getState().getStateName());

        dto.setCountryId(entity.getState().getCountry().getId());

        dto.setCountryName(entity.getState().getCountry().getCountryName());

        dto.setCreatedAt(entity.getCreatedAt());

        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public void updateEntity(District entity,
                             DistrictRequestDto dto,
                             State state) {

        entity.setDistrictName(dto.getDistrictName().trim());

        entity.setDistrictCode(dto.getDistrictCode().trim().toUpperCase());

        entity.setState(state);

    }

}