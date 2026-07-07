package com.example.student.mapper;

import org.springframework.stereotype.Component;

import com.example.student.dto.StudentRequestDto;
import com.example.student.dto.StudentResponseDto;
import com.example.student.entity.Student;

@Component
public class StudentMapper {

	public Student convertToEntity(StudentRequestDto dto) {

	    Student student = new Student();

	    student.setRollNumber(dto.getRollNumber());
	    student.setFirstName(dto.getFirstName());
	    student.setLastName(dto.getLastName());
	    student.setEmail(dto.getEmail());
	    student.setPhoneNumber(dto.getPhoneNumber());
	    student.setGender(dto.getGender());
	    student.setDateOfBirth(dto.getDateOfBirth());
	    student.setAdmissionDate(dto.getAdmissionDate());
	    student.setFees(dto.getFees());
	    student.setStatus(dto.getStatus());
	    student.setProfileImage(dto.getProfileImage());
	    

	    return student;
	}
	
	
	public StudentResponseDto convertToResponseDto(Student student) {

	    StudentResponseDto dto = new StudentResponseDto();

	    dto.setId(student.getId());
	    dto.setRollNumber(student.getRollNumber());
	    dto.setFirstName(student.getFirstName());
	    dto.setLastName(student.getLastName());
	    dto.setEmail(student.getEmail());
	    dto.setPhoneNumber(student.getPhoneNumber());
	    dto.setGender(student.getGender());
	    dto.setDateOfBirth(student.getDateOfBirth());
	    dto.setAdmissionDate(student.getAdmissionDate());
	    dto.setFees(student.getFees());
	    dto.setStatus(student.getStatus());
	    dto.setProfileImage(student.getProfileImage());

	    if(student.getDepartment()!=null){
	        dto.setDepartmentId(student.getDepartment().getId());
	        dto.setDepartmentName(student.getDepartment().getDepartmentName());
	    }
	    
	    dto.setCreatedAt(student.getCreatedAt());
	    dto.setUpdatedAt(student.getUpdatedAt());
	    
		/*
		 * dto.setVillage(student.getVillage().getVillageName());
		 * 
		 * dto.setPincode(student.getVillage().getPincode());
		 * 
		 * dto.setTehsil( student.getVillage() .getTehsil() .getTehsilName());
		 * 
		 * dto.setDistrict( student.getVillage() .getTehsil() .getDistrict()
		 * .getDistrictName());
		 * 
		 * dto.setState( student.getVillage() .getTehsil() .getDistrict() .getState()
		 * .getStateName());
		 * 
		 * dto.setCountry( student.getVillage() .getTehsil() .getDistrict() .getState()
		 * .getCountry() .getCountryName());
		 */
	    

	    return dto;
	}
	
	
	
	public void updateEntity(Student student, StudentRequestDto dto) {
		student.setRollNumber(dto.getRollNumber());
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setEmail(dto.getEmail());
		student.setPhoneNumber(dto.getPhoneNumber());
		student.setGender(dto.getGender());
		student.setDateOfBirth(dto.getDateOfBirth());
		student.setAdmissionDate(dto.getAdmissionDate());
		student.setFees(dto.getFees());
		student.setStatus(dto.getStatus());
		student.setProfileImage(dto.getProfileImage());
		
	}
	
}
