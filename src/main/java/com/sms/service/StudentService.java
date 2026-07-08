package com.sms.service;

import com.sms.dto.PageResponse;
import com.sms.dto.StudentRequestDto;
import com.sms.dto.StudentResponseDto;
import com.sms.dto.StudentSearchRequest;

public interface StudentService {

	StudentResponseDto saveStudent(StudentRequestDto requestDto);

	StudentResponseDto getStudentById(Long id);

	StudentResponseDto updateStudent(Long id, StudentRequestDto dto);

	void deleteStudent(Long id);
	
	
	PageResponse<StudentResponseDto> getAllStudents(int page, int size, String sortBy, String direction);
	
	
	PageResponse<StudentResponseDto> searchStudents(
	        StudentSearchRequest request,
	        int page,
	        int size,
	        String sortBy,
	        String direction
	);
	
}
