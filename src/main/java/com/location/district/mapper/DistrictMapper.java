package com.location.district.mapper;


import org.springframework.stereotype.Component;

import com.location.common.util.LocationUtils;
import com.location.district.dto.DistrictRequestDto;
import com.location.district.dto.DistrictResponseDto;
import com.location.district.entity.District;
import com.location.enums.MasterStatus;
import com.location.state.entity.State;


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