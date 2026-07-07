package com.example.student.location.district.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.student.location.district.dto.DistrictRequestDto;
import com.example.student.location.district.dto.DistrictResponseDto;
import com.example.student.location.district.dto.DistrictSearchDto;

public interface DistrictService {

    DistrictResponseDto createDistrict(DistrictRequestDto dto);

    DistrictResponseDto updateDistrict(Long id,
                                       DistrictRequestDto dto);

    DistrictResponseDto getDistrictById(Long id);

    Page<DistrictResponseDto> getAllDistricts(Pageable pageable);

    Page<DistrictResponseDto> searchDistricts(DistrictSearchDto dto,
                                              Pageable pageable);

    void deleteDistrict(Long id);

}
