package com.location.address.service;

import java.util.List;

import com.location.address.dto.AddressRequestDto;
import com.location.address.dto.AddressResponseDto;

public interface AddressService {

	AddressResponseDto createAddress(AddressRequestDto request);

	AddressResponseDto getAddressById(Long id);

	List<AddressResponseDto> getAllAddresses();

	AddressResponseDto updateAddress(Long id, AddressRequestDto request);

	void deleteAddress(Long id);

}