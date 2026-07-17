package com.location.address.serviceImpl;

import java.util.List;

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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final VillageRepository villageRepository;
    private final AddressMapper addressMapper;

    // ==========================================================
    // CREATE ADDRESS (REST API)
    // ==========================================================

    @Override
    public AddressResponseDto createAddress(AddressRequestDto request) {

        Address address = saveAddress(request);

        return addressMapper.convertToResponseDto(address);
    }

    // ==========================================================
    // GET ADDRESS BY ID
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDto getAddressById(Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address not found with id : " + id));

        return addressMapper.convertToResponseDto(address);
    }

    // ==========================================================
    // GET ALL ADDRESSES
    // ==========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDto> getAllAddresses() {

        return addressRepository.findAll()
                .stream()
                .map(addressMapper::convertToResponseDto)
                .toList();
    }

    // ==========================================================
    // UPDATE ADDRESS (REST API)
    // ==========================================================

    @Override
    public AddressResponseDto updateAddress(
            Long id,
            AddressRequestDto request
    ) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address not found with id : " + id));

        Address updatedAddress = updateAddress(address, request);

        return addressMapper.convertToResponseDto(updatedAddress);
    }

    // ==========================================================
    // DELETE ADDRESS
    // ==========================================================

    @Override
    public void deleteAddress(Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address not found with id : " + id));

        addressRepository.delete(address);
    }

    // ==========================================================
    // INTERNAL SAVE METHOD
    // Used by Student, Faculty, Employee etc.
    // ==========================================================

    @Override
    public Address saveAddress(AddressRequestDto dto) {

        Village village = villageRepository.findById(dto.getVillageId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Village not found"));

        Address address = addressMapper.convertToEntity(dto);

        address.setVillage(village);

        return addressRepository.save(address);
    }

    // ==========================================================
    // INTERNAL UPDATE METHOD
    // Used by Student, Faculty, Employee etc.
    // ==========================================================

    @Override
    public Address updateAddress(
            Address address,
            AddressRequestDto dto
    ) {

        Village village = villageRepository.findById(dto.getVillageId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Village not found"));

        addressMapper.updateEntity(address, dto);

        address.setVillage(village);

        return addressRepository.save(address);
    }

}