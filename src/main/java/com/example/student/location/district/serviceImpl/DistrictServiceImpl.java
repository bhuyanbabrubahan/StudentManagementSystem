package com.example.student.location.district.serviceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student.exception.DuplicateResourceException;
import com.example.student.location.common.MasterStatus;
import com.example.student.location.common.service.LocationValidationService;
import com.example.student.location.district.dto.DistrictRequestDto;
import com.example.student.location.district.dto.DistrictResponseDto;
import com.example.student.location.district.dto.DistrictSearchDto;
import com.example.student.location.district.entity.District;
import com.example.student.location.district.mapper.DistrictMapper;
import com.example.student.location.district.repository.DistrictRepository;
import com.example.student.location.district.service.DistrictService;
import com.example.student.location.district.specification.DistrictSpecification;
import com.example.student.location.state.entity.State;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    private final DistrictMapper districtMapper;

    private final LocationValidationService validationService;

    @Override
    public DistrictResponseDto createDistrict(DistrictRequestDto dto) {

        State state =
                validationService.validateState(dto.getStateId());

        if (districtRepository.existsByStateIdAndDistrictNameIgnoreCase(
                state.getId(),
                dto.getDistrictName().trim())) {

            throw new DuplicateResourceException(
                    "District already exists.");
        }

        if (districtRepository.existsByDistrictCodeIgnoreCase(
                dto.getDistrictCode().trim())) {

            throw new DuplicateResourceException(
                    "District code already exists.");
        }

        District district =
                districtMapper.toEntity(dto, state);

        return districtMapper.toDto(
                districtRepository.save(district));
    }

    @Override
    public DistrictResponseDto updateDistrict(
            Long id,
            DistrictRequestDto dto) {

        District district =
                validationService.validateDistrict(id);

        State state =
                validationService.validateState(dto.getStateId());

        if (districtRepository
                .existsByStateIdAndDistrictNameIgnoreCaseAndIdNot(
                        state.getId(),
                        dto.getDistrictName().trim(),
                        id)) {

            throw new DuplicateResourceException(
                    "District already exists.");
        }

        if (districtRepository
                .existsByDistrictCodeIgnoreCaseAndIdNot(
                        dto.getDistrictCode().trim(),
                        id)) {

            throw new DuplicateResourceException(
                    "District code already exists.");
        }

        districtMapper.updateEntity(
                district,
                dto,
                state);

        return districtMapper.toDto(
                districtRepository.save(district));
    }

    @Override
    @Transactional(readOnly = true)
    public DistrictResponseDto getDistrictById(Long id) {

        return districtMapper.toDto(
                validationService.validateDistrict(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DistrictResponseDto> getAllDistricts(
            Pageable pageable) {

        return districtRepository
                .findAll(
                        DistrictSpecification.search(
                                new DistrictSearchDto()),
                        pageable)
                .map(districtMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DistrictResponseDto> searchDistricts(
            DistrictSearchDto dto,
            Pageable pageable) {

        return districtRepository
                .findAll(
                        DistrictSpecification.search(dto),
                        pageable)
                .map(districtMapper::toDto);
    }

    @Override
    public void deleteDistrict(Long id) {

        District district =
                validationService.validateDistrict(id);

        district.setStatus(MasterStatus.DELETED);

        districtRepository.save(district);
    }

}