package com.sms.mapper;

import org.springframework.stereotype.Component;

import com.sms.dto.StudentRequestDto;
import com.sms.dto.StudentResponseDto;
import com.sms.entity.Student;
import com.sms.security.dto.RegisterRequestDto;

@Component
public class StudentMapper {

	public Student convertToEntity(StudentRequestDto dto) {

	    Student student = new Student();

	    student.setRollNumber(dto.getRollNumber());
	    student.setFirstName(dto.getFirstName());
	    student.setLastName(dto.getLastName());
	    student.setGender(dto.getGender());
	    student.setDateOfBirth(dto.getDateOfBirth());
	    student.setAdmissionDate(dto.getAdmissionDate());
	    student.setFees(dto.getFees());
	    student.setStatus(dto.getStatus());
	    student.setProfileImage(dto.getProfileImage());
	    

	    return student;
	}
	
	public Student convertToEntity(RegisterRequestDto dto) {

	    Student student = new Student();

	    student.setFirstName(dto.getFirstName());

	    student.setLastName(dto.getLastName());

	    student.setPhoneNumber(dto.getPhoneNumber());

	    student.setGender(dto.getGender());

	    student.setDateOfBirth(dto.getDateOfBirth());


	    return student;
	}
	
	
	public StudentResponseDto convertToResponseDto(Student student) {

	    StudentResponseDto dto = new StudentResponseDto();

	    dto.setId(student.getId());
	    dto.setRollNumber(student.getRollNumber());
	    dto.setFirstName(student.getFirstName());
	    dto.setLastName(student.getLastName());
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
	    
	    return dto;
	}
	
	
	
	public void updateEntity(Student student, StudentRequestDto dto) {
		student.setRollNumber(dto.getRollNumber());
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setGender(dto.getGender());
		student.setDateOfBirth(dto.getDateOfBirth());
		student.setAdmissionDate(dto.getAdmissionDate());
		student.setFees(dto.getFees());
		student.setStatus(dto.getStatus());
		student.setProfileImage(dto.getProfileImage());
		
	}
	
}
