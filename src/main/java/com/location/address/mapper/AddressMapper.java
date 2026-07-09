package com.location.address.mapper;

import org.springframework.stereotype.Component;

import com.location.address.dto.AddressRequestDto;
import com.location.address.dto.AddressResponseDto;
import com.location.address.entity.Address;

@Component
public class AddressMapper {

	public Address convertToEntity(AddressRequestDto dto) {

		Address address = new Address();

		address.setHouseNumber(dto.getHouseNumber());

		address.setStreet(dto.getStreet());

		address.setLandmark(dto.getLandmark());

		address.setPostalCode(dto.getPostalCode());

		address.setAddressType(dto.getAddressType());

		return address;

	}

	public void updateEntity(Address address, AddressRequestDto dto) {

		address.setHouseNumber(dto.getHouseNumber());

		address.setStreet(dto.getStreet());

		address.setLandmark(dto.getLandmark());

		address.setPostalCode(dto.getPostalCode());

		address.setAddressType(dto.getAddressType());

	}

	public AddressResponseDto convertToResponseDto(Address address) {

		AddressResponseDto response = new AddressResponseDto();

		response.setId(address.getId());

		response.setHouseNumber(address.getHouseNumber());

		response.setStreet(address.getStreet());

		response.setLandmark(address.getLandmark());

		response.setPostalCode(address.getPostalCode());

		response.setAddressType(address.getAddressType());

		response.setVillageId(address.getVillage().getId());

		response.setVillageName(address.getVillage().getVillageName());

		return response;

	}

}