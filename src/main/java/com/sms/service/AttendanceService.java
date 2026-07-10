package com.sms.service;

import com.sms.dto.AttendanceRequestDto;
import com.sms.dto.AttendanceResponseDto;
import com.sms.dto.AttendanceSearchRequest;
import com.sms.dto.PageResponse;

public interface AttendanceService {


    AttendanceResponseDto createAttendance(
            AttendanceRequestDto dto
    );


    AttendanceResponseDto getAttendanceById(
            Long id
    );


    PageResponse<AttendanceResponseDto> getAllAttendance(
            int page,
            int size,
            String sortBy,
            String direction
    );


    AttendanceResponseDto updateAttendance(
            Long id,
            AttendanceRequestDto dto
    );


    void deleteAttendance(
            Long id
    );


    PageResponse<AttendanceResponseDto> searchAttendance(
            AttendanceSearchRequest request,
            int page,
            int size,
            String sortBy,
            String direction
    );

    
    
}