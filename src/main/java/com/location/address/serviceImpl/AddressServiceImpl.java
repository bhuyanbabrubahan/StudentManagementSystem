package com.location.address.serviceImpl;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.location.address.dto.AddressRequestDto;
import com.location.address.dto.AddressResponseDto;
import com.location.address.entity.Address;
import com.location.address.mapper.AddressMapper;
import com.location.address.repository.AddressRepository;
import com.location.address.service.AddressService;
import com.location.village.entity.Village;
import com.location.village.repository.VillageRepository;
import com.sms.exception.ResourceNotFoundException;

@Service
public class AddressServiceImpl implements AddressService {

	private final AddressRepository addressRepository;

	private final VillageRepository villageRepository;

	private final AddressMapper addressMapper;

	public AddressServiceImpl(AddressRepository addressRepository, VillageRepository villageRepository,
			AddressMapper addressMapper) {

		this.addressRepository = addressRepository;

		this.villageRepository = villageRepository;

		this.addressMapper = addressMapper;

	}

	// CREATE
	@Override
	@Transactional
	public AddressResponseDto createAddress(AddressRequestDto request) {

		Village village = villageRepository.findById(request.getVillageId())
				.orElseThrow(() -> new ResourceNotFoundException("Village not found"));

		Address address = addressMapper.convertToEntity(request);

		address.setVillage(village);

		Address savedAddress = addressRepository.save(address);

		return addressMapper.convertToResponseDto(savedAddress);

	}

	// GET BY ID
	@Override
	@Transactional(readOnly = true)
	public AddressResponseDto getAddressById(Long id) {

		Address address = addressRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Address not found with id : " + id));

		return addressMapper.convertToResponseDto(address);

	}

	// GET ALL
	@Override
	@Transactional(readOnly = true)
	public List<AddressResponseDto> getAllAddresses() {

		return addressRepository.findAll().stream().map(addressMapper::convertToResponseDto)
				.collect(Collectors.toList());

	}

	// UPDATE
	@Override
	@Transactional
	public AddressResponseDto updateAddress(Long id, AddressRequestDto request) {

		Address address = addressRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Address not found with id : " + id));

		Village village = villageRepository.findById(request.getVillageId())
				.orElseThrow(() -> new ResourceNotFoundException("Village not found"));

		addressMapper.updateEntity(address, request);

		address.setVillage(village);

		Address updatedAddress = addressRepository.save(address);

		return addressMapper.convertToResponseDto(updatedAddress);

	}

	// DELETE
	@Override
	@Transactional
	public void deleteAddress(Long id) {

		Address address = addressRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Address not found with id : " + id));

		addressRepository.delete(address);

	}

}