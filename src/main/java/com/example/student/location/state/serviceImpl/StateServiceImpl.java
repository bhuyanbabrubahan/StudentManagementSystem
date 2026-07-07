package com.example.student.location.state.serviceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student.exception.DuplicateResourceException;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.location.common.MasterStatus;
import com.example.student.location.common.service.LocationValidationService;
import com.example.student.location.country.entity.Country;
import com.example.student.location.country.repository.CountryRepository;
import com.example.student.location.state.dto.StateRequestDto;
import com.example.student.location.state.dto.StateResponseDto;
import com.example.student.location.state.dto.StateSearchDto;
import com.example.student.location.state.entity.State;
import com.example.student.location.state.mapper.StateMapper;
import com.example.student.location.state.repository.StateRepository;
import com.example.student.location.state.service.StateService;
import com.example.student.location.state.specification.StateSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    private final CountryRepository countryRepository;

    private final StateMapper stateMapper;
    
    private final LocationValidationService locationValidationService;

    @Override
    public StateResponseDto createState(StateRequestDto dto) {

    	Country country =
    	        locationValidationService.validateCountry(dto.getCountryId());

        if (stateRepository.existsByCountryIdAndStateNameIgnoreCase(
                country.getId(),
                dto.getStateName().trim())) {

            throw new DuplicateResourceException("State already exists.");
        }

        if (stateRepository.existsByStateCodeIgnoreCase(
                dto.getStateCode().trim())) {

            throw new DuplicateResourceException("State code already exists.");
        }

        State state = stateMapper.toEntity(dto, country);

        State saved = stateRepository.save(state);

        return stateMapper.toDto(saved);
    }

    @Override
    public StateResponseDto updateState(Long id,
                                        StateRequestDto dto) {

        State state = stateRepository
                .findByIdAndStatus(id, MasterStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("State not found."));

        Country country =
                locationValidationService.validateCountry(dto.getCountryId());

        if (stateRepository.existsByCountryIdAndStateNameIgnoreCaseAndIdNot(
                country.getId(),
                dto.getStateName().trim(),
                id)) {

            throw new DuplicateResourceException("State already exists.");
        }

        if (stateRepository.existsByStateCodeIgnoreCaseAndIdNot(
                dto.getStateCode().trim(),
                id)) {

            throw new DuplicateResourceException("State code already exists.");
        }

        stateMapper.updateEntity(state, dto, country);

        State updated = stateRepository.save(state);

        return stateMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public StateResponseDto getStateById(Long id) {

        State state = stateRepository
                .findByIdAndStatus(id, MasterStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("State not found."));

        return stateMapper.toDto(state);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StateResponseDto> getAllStates(Pageable pageable) {

        return stateRepository
                .findAll(StateSpecification.search(new StateSearchDto()), pageable)
                .map(stateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StateResponseDto> searchStates(StateSearchDto dto,
                                               Pageable pageable) {

        return stateRepository
                .findAll(StateSpecification.search(dto), pageable)
                .map(stateMapper::toDto);
    }

    @Override
    public void deleteState(Long id) {

        State state = stateRepository
                .findByIdAndStatus(id, MasterStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("State not found."));

        state.setStatus(MasterStatus.DELETED);

        stateRepository.save(state);
    }

}