package com.example.student.service;

import com.example.student.dto.DepartmentRequestDto;
import com.example.student.dto.DepartmentResponseDto;
import com.example.student.dto.DepartmentSearchRequest;
import com.example.student.dto.PageResponse;

public interface DepartmentService {

    DepartmentResponseDto createDepartment(DepartmentRequestDto dto);

    DepartmentResponseDto getDepartmentById(Long id);
    
    PageResponse<DepartmentResponseDto> getAllDepartments(int page, int size, String sortBy, String direction);
    
    DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto dto);

    void deleteDepartment(Long id);
    
    PageResponse<DepartmentResponseDto> searchDepartments(
	        DepartmentSearchRequest request,
	        int page,
	        int size,
	        String sortBy,
	        String direction);
}
