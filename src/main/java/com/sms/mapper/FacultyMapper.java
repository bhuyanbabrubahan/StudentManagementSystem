package com.sms.mapper;

import org.springframework.stereotype.Component;

import com.sms.dto.FacultyRequestDto;
import com.sms.dto.FacultyResponseDto;
import com.sms.entity.Faculty;

@Component
public class FacultyMapper {

	// DTO -> Entity
	public Faculty convertToEntity(FacultyRequestDto dto) {

		Faculty faculty = new Faculty();

		faculty.setFirstName(dto.getFirstName());

		faculty.setLastName(dto.getLastName());

		faculty.setPhoneNumber(dto.getPhoneNumber());

		faculty.setGender(dto.getGender());

		faculty.setDateOfBirth(dto.getDateOfBirth());

		faculty.setJoiningDate(dto.getJoiningDate());

		faculty.setDesignation(dto.getDesignation());

		faculty.setQualification(dto.getQualification());

		faculty.setSalary(dto.getSalary());

		faculty.setProfileImage(dto.getProfileImage());

		return faculty;
	}

	// Entity -> DTO
	public FacultyResponseDto convertToResponseDto(Faculty faculty) {

		FacultyResponseDto dto = new FacultyResponseDto();

		dto.setId(faculty.getId());

		// User Mapping

		if (faculty.getUser() != null) {

			dto.setUserId(faculty.getUser().getId());

		}

		dto.setEmployeeCode(faculty.getEmployeeCode());

		dto.setFirstName(faculty.getFirstName());

		dto.setLastName(faculty.getLastName());

		dto.setPhoneNumber(faculty.getPhoneNumber());

		dto.setGender(faculty.getGender());

		dto.setDateOfBirth(faculty.getDateOfBirth());

		dto.setJoiningDate(faculty.getJoiningDate());

		dto.setDesignation(faculty.getDesignation());

		dto.setQualification(faculty.getQualification());

		dto.setSalary(faculty.getSalary());

		dto.setProfileImage(faculty.getProfileImage());

		dto.setStatus(faculty.getStatus());

		// Department Mapping

		if (faculty.getDepartment() != null) {

			dto.setDepartmentId(faculty.getDepartment().getId());

			dto.setDepartmentName(faculty.getDepartment().getDepartmentName());

		}

		// Address Mapping

		if (faculty.getAddress() != null) {

			dto.setAddressId(faculty.getAddress().getId());

			if (faculty.getAddress().getVillage() != null) {

				dto.setVillageName(faculty.getAddress().getVillage().getVillageName());

			}

		}

		dto.setCreatedAt(faculty.getCreatedAt());

		dto.setUpdatedAt(faculty.getUpdatedAt());

		return dto;

	}
	
	
	

	// Update Existing Entity
	public void updateEntity(Faculty faculty, FacultyRequestDto dto) {

		faculty.setFirstName(dto.getFirstName());

		faculty.setLastName(dto.getLastName());

		faculty.setPhoneNumber(dto.getPhoneNumber());

		faculty.setGender(dto.getGender());

		faculty.setDateOfBirth(dto.getDateOfBirth());

		faculty.setJoiningDate(dto.getJoiningDate());

		faculty.setDesignation(dto.getDesignation());

		faculty.setQualification(dto.getQualification());

		faculty.setSalary(dto.getSalary());

		faculty.setProfileImage(dto.getProfileImage());

	}

}