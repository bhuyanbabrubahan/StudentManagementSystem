package com.example.student.location.tehsil.serviceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student.exception.DuplicateResourceException;
import com.example.student.location.common.MasterStatus;
import com.example.student.location.common.service.LocationValidationService;
import com.example.student.location.district.entity.District;
import com.example.student.location.tehsil.dto.TehsilRequestDto;
import com.example.student.location.tehsil.dto.TehsilResponseDto;
import com.example.student.location.tehsil.dto.TehsilSearchDto;
import com.example.student.location.tehsil.entity.Tehsil;
import com.example.student.location.tehsil.mapper.TehsilMapper;
import com.example.student.location.tehsil.repository.TehsilRepository;
import com.example.student.location.tehsil.service.TehsilService;
import com.example.student.location.tehsil.specification.TehsilSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TehsilServiceImpl implements TehsilService {

	private final TehsilRepository tehsilRepository;

	private final TehsilMapper tehsilMapper;

	private final LocationValidationService validationService;

	@Override
	public TehsilResponseDto createTehsil(TehsilRequestDto dto) {

		District district = validationService.validateDistrict(dto.getDistrictId());

		if (tehsilRepository.existsByDistrictIdAndTehsilNameIgnoreCase(district.getId(), dto.getTehsilName().trim())) {

			throw new DuplicateResourceException("Tehsil already exists.");
		}

		Tehsil tehsil = tehsilMapper.toEntity(dto, district);

		return tehsilMapper.toDto(tehsilRepository.save(tehsil));
	}

	@Override
	public TehsilResponseDto updateTehsil(Long id, TehsilRequestDto dto) {

		Tehsil tehsil = validationService.validateTehsil(id);

		District district = validationService.validateDistrict(dto.getDistrictId());

		if (tehsilRepository.existsByDistrictIdAndTehsilNameIgnoreCaseAndIdNot(district.getId(),
				dto.getTehsilName().trim(), id)) {

			throw new DuplicateResourceException("Tehsil already exists.");
		}

		tehsilMapper.updateEntity(tehsil, dto, district);

		return tehsilMapper.toDto(tehsilRepository.save(tehsil));
	}

	@Override
	@Transactional(readOnly = true)
	public TehsilResponseDto getTehsilById(Long id) {

		return tehsilMapper.toDto(validationService.validateTehsil(id));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TehsilResponseDto> getAllTehsils(Pageable pageable) {

		return tehsilRepository.findAll(TehsilSpecification.search(new TehsilSearchDto()), pageable)
				.map(tehsilMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TehsilResponseDto> searchTehsils(TehsilSearchDto dto, Pageable pageable) {

		return tehsilRepository.findAll(TehsilSpecification.search(dto), pageable).map(tehsilMapper::toDto);
	}

	@Override
	public void deleteTehsil(Long id) {

		Tehsil tehsil = validationService.validateTehsil(id);

		tehsil.setStatus(MasterStatus.DELETED);

		tehsilRepository.save(tehsil);

	}

}
