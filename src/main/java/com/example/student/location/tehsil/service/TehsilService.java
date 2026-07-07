package com.example.student.location.tehsil.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.student.location.tehsil.dto.TehsilRequestDto;
import com.example.student.location.tehsil.dto.TehsilResponseDto;
import com.example.student.location.tehsil.dto.TehsilSearchDto;

public interface TehsilService {

    TehsilResponseDto createTehsil(TehsilRequestDto dto);

    TehsilResponseDto updateTehsil(Long id,
                                   TehsilRequestDto dto);

    TehsilResponseDto getTehsilById(Long id);

    Page<TehsilResponseDto> getAllTehsils(Pageable pageable);

    Page<TehsilResponseDto> searchTehsils(TehsilSearchDto dto,
                                          Pageable pageable);

    void deleteTehsil(Long id);

}