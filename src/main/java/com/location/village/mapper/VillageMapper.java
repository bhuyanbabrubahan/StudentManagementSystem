package com.location.village.mapper;

import org.springframework.stereotype.Component;

import com.location.common.util.LocationUtils;
import com.location.enums.MasterStatus;
import com.location.tehsil.entity.Tehsil;
import com.location.village.dto.VillageRequestDto;
import com.location.village.dto.VillageResponseDto;
import com.location.village.entity.Village;

@Component
public class VillageMapper {

	public Village toEntity(VillageRequestDto dto, Tehsil tehsil) {

		Village village = new Village();

		village.setVillageName(LocationUtils.normalizeName(dto.getVillageName()));

		village.setPincode(LocationUtils.normalizePincode(dto.getPincode()));

		village.setTehsil(tehsil);

		village.setStatus(MasterStatus.ACTIVE);

		return village;
	}

	public void updateEntity(Village village, VillageRequestDto dto, Tehsil tehsil) {

		village.setVillageName(LocationUtils.normalizeName(dto.getVillageName()));

		village.setPincode(LocationUtils.normalizePincode(dto.getPincode()));

		village.setTehsil(tehsil);

	}

	public VillageResponseDto toDto(Village entity) {

		VillageResponseDto dto = new VillageResponseDto();

		dto.setId(entity.getId());

		dto.setVillageName(entity.getVillageName());

		dto.setPincode(entity.getPincode());

		dto.setStatus(entity.getStatus());

		dto.setTehsilId(entity.getTehsil().getId());

		dto.setTehsilName(entity.getTehsil().getTehsilName());

		dto.setDistrictId(entity.getTehsil().getDistrict().getId());

		dto.setDistrictName(entity.getTehsil().getDistrict().getDistrictName());

		dto.setStateId(entity.getTehsil().getDistrict().getState().getId());

		dto.setStateName(entity.getTehsil().getDistrict().getState().getStateName());

		dto.setCountryId(entity.getTehsil().getDistrict().getState().getCountry().getId());

		dto.setCountryName(entity.getTehsil().getDistrict().getState().getCountry().getCountryName());

		dto.setCreatedAt(entity.getCreatedAt());

		dto.setUpdatedAt(entity.getUpdatedAt());

		return dto;
	}

}