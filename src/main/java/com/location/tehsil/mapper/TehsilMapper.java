package com.location.tehsil.mapper;

import org.springframework.stereotype.Component;

import com.location.common.util.LocationUtils;
import com.location.district.entity.District;
import com.location.enums.MasterStatus;
import com.location.tehsil.dto.TehsilRequestDto;
import com.location.tehsil.dto.TehsilResponseDto;
import com.location.tehsil.entity.Tehsil;

@Component
public class TehsilMapper {

    public Tehsil toEntity(
            TehsilRequestDto dto,
            District district) {

        Tehsil tehsil = new Tehsil();

        tehsil.setTehsilName(
                LocationUtils.normalizeName(dto.getTehsilName()));

        tehsil.setDistrict(district);

        tehsil.setStatus(MasterStatus.ACTIVE);

        return tehsil;
    }

    public void updateEntity(
            Tehsil tehsil,
            TehsilRequestDto dto,
            District district) {

        tehsil.setTehsilName(
                LocationUtils.normalizeName(dto.getTehsilName()));

        tehsil.setDistrict(district);

    }

    public TehsilResponseDto toDto(
            Tehsil entity) {

        TehsilResponseDto dto =
                new TehsilResponseDto();

        dto.setId(entity.getId());

        dto.setTehsilName(entity.getTehsilName());

        dto.setStatus(entity.getStatus());

        dto.setDistrictId(
                entity.getDistrict().getId());

        dto.setDistrictName(
                entity.getDistrict().getDistrictName());

        dto.setStateId(
                entity.getDistrict().getState().getId());

        dto.setStateName(
                entity.getDistrict().getState().getStateName());

        dto.setCountryId(
                entity.getDistrict().getState().getCountry().getId());

        dto.setCountryName(
                entity.getDistrict().getState().getCountry().getCountryName());

        dto.setCreatedAt(entity.getCreatedAt());

        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;

    }

}