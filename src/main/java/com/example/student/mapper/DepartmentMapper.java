package com.example.student.mapper;

import org.springframework.stereotype.Component;

import com.example.student.dto.DepartmentRequestDto;
import com.example.student.dto.DepartmentResponseDto;
import com.example.student.entity.Department;



@Component
public class DepartmentMapper {

	public Department toEntity (DepartmentRequestDto dto) {
		
		Department dept = new Department();
		
		dept.setDepartmentCode(dto.getDepartmentCode());
		dept.setDepartmentName(dto.getDepartmentName());
		dept.setDescription(dto.getDescription());
		
		return dept;
		
	}
	
	public DepartmentResponseDto toDto(Department dept) {
		
		DepartmentResponseDto dto = new DepartmentResponseDto();
		
		dto.setId(dept.getId());
		dto.setDepartmentName(dept.getDepartmentName());
		dto.setDepartmentCode(dept.getDepartmentCode());
		dto.setDescription(dept.getDescription());
		dto.setStatus(dept.getStatus());

		dto.setCreatedAt(dept.getCreatedAt());
		dto.setUpdatedAt(dept.getUpdatedAt());
		
		return dto;
		
	}
    
	
	
	
	
	public void updateEntity(Department department, DepartmentRequestDto dto) {
		
		department.setDepartmentCode(dto.getDepartmentCode());
		department.setDepartmentName(dto.getDepartmentName());
		department.setDescription(dto.getDescription());
		department.setStatus(dto.getStatus());
	}
	
	
	
	
}