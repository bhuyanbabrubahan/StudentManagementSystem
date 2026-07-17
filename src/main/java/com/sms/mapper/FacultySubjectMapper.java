package com.sms.mapper;

import org.springframework.stereotype.Component;

import com.sms.dto.FacultySubjectRequestDto;
import com.sms.dto.FacultySubjectResponseDto;
import com.sms.entity.FacultySubjectMapping;

@Component
public class FacultySubjectMapper {

	// Entity To Response DTO

	public FacultySubjectResponseDto toDto(FacultySubjectMapping entity) {

		FacultySubjectResponseDto dto = new FacultySubjectResponseDto();

		dto.setId(entity.getId());

		dto.setFacultyId(entity.getFaculty().getId());

		dto.setFacultyName(entity.getFaculty().getFirstName());

		dto.setSubjectId(entity.getSubject().getId());

		dto.setSubjectName(entity.getSubject().getSubjectName());

		dto.setAssignedDate(entity.getAssignedDate());

		dto.setAcademicYear(entity.getAcademicYear());

		dto.setStatus(entity.getStatus());

		return dto;
	}

	// Request DTO -> Entity

	public FacultySubjectMapping toEntity(FacultySubjectRequestDto dto) {

		FacultySubjectMapping entity = new FacultySubjectMapping();

		entity.setAssignedDate(dto.getAssignedDate());

		entity.setAcademicYear(dto.getAcademicYear());

		return entity;

	}

}