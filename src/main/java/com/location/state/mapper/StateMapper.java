package com.location.state.mapper;

import org.springframework.stereotype.Component;

import com.location.country.entity.Country;
import com.location.enums.MasterStatus;
import com.location.state.dto.StateRequestDto;
import com.location.state.dto.StateResponseDto;
import com.location.state.entity.State;

@Component
public class StateMapper {

    public State toEntity(StateRequestDto dto, Country country) {

        State state = new State();

        state.setStateName(dto.getStateName().trim());

        state.setStateCode(dto.getStateCode().trim().toUpperCase());

        state.setCountry(country);

        state.setStatus(MasterStatus.ACTIVE);

        return state;
    }

    public StateResponseDto toDto(State entity) {

        StateResponseDto dto = new StateResponseDto();

        dto.setId(entity.getId());

        dto.setStateName(entity.getStateName());

        dto.setStateCode(entity.getStateCode());

        dto.setStatus(entity.getStatus());

        dto.setCountryId(entity.getCountry().getId());

        dto.setCountryName(entity.getCountry().getCountryName());

        dto.setCreatedAt(entity.getCreatedAt());

        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public void updateEntity(State entity,
                             StateRequestDto dto,
                             Country country) {

        entity.setStateName(dto.getStateName().trim());

        entity.setStateCode(dto.getStateCode().trim().toUpperCase());

        entity.setCountry(country);

    }

}