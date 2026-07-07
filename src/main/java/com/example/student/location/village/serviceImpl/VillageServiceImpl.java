package com.example.student.location.village.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student.exception.DuplicateResourceException;
import com.example.student.location.common.MasterStatus;
import com.example.student.location.common.service.LocationValidationService;
import com.example.student.location.tehsil.entity.Tehsil;
import com.example.student.location.village.dto.VillageRequestDto;
import com.example.student.location.village.dto.VillageResponseDto;
import com.example.student.location.village.dto.VillageSearchDto;
import com.example.student.location.village.entity.Village;
import com.example.student.location.village.mapper.VillageMapper;
import com.example.student.location.village.repository.VillageRepository;
import com.example.student.location.village.service.VillageService;
import com.example.student.location.village.specification.VillageSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VillageServiceImpl implements VillageService {

	private final VillageRepository villageRepository;

	private final VillageMapper villageMapper;

	private final LocationValidationService validationService;

	@Override
	public VillageResponseDto createVillage(VillageRequestDto dto) {

		Tehsil tehsil = validationService.validateTehsil(dto.getTehsilId());

		if (villageRepository.existsByTehsilIdAndVillageNameIgnoreCaseAndPincode(tehsil.getId(),
				dto.getVillageName().trim(), dto.getPincode().trim())) {

			throw new DuplicateResourceException("Village already exists.");
		}

		Village village = villageMapper.toEntity(dto, tehsil);

		return villageMapper.toDto(villageRepository.save(village));
	}

	@Override
	public VillageResponseDto updateVillage(Long id, VillageRequestDto dto) {

		Village village = validationService.validateVillage(id);

		Tehsil tehsil = validationService.validateTehsil(dto.getTehsilId());

		if (villageRepository.existsByTehsilIdAndVillageNameIgnoreCaseAndPincodeAndIdNot(tehsil.getId(),
				dto.getVillageName().trim(), dto.getPincode().trim(), id)) {

			throw new DuplicateResourceException("Village already exists.");
		}

		villageMapper.updateEntity(village, dto, tehsil);

		return villageMapper.toDto(villageRepository.save(village));
	}

	@Override
	@Transactional(readOnly = true)
	public VillageResponseDto getVillageById(Long id) {

		return villageMapper.toDto(validationService.validateVillage(id));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VillageResponseDto> getAllVillages(Pageable pageable) {

		return villageRepository.findAll(VillageSpecification.search(new VillageSearchDto()), pageable)
				.map(villageMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<VillageResponseDto> searchVillages(VillageSearchDto dto, Pageable pageable) {

		return villageRepository.findAll(VillageSpecification.search(dto), pageable).map(villageMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VillageResponseDto> getVillagesByTehsil(Long tehsilId) {

		validationService.validateTehsil(tehsilId);

		return villageRepository.findByTehsilIdAndStatusOrderByVillageNameAsc(tehsilId, MasterStatus.ACTIVE).stream()
				.map(villageMapper::toDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VillageResponseDto> getVillagesByPincode(String pincode) {

		return villageRepository.findByPincodeAndStatusOrderByVillageNameAsc(pincode, MasterStatus.ACTIVE).stream()
				.map(villageMapper::toDto).toList();
	}

	@Override
	public void deleteVillage(Long id) {

		Village village = validationService.validateVillage(id);

		village.setStatus(MasterStatus.DELETED);

		villageRepository.save(village);

	}

}