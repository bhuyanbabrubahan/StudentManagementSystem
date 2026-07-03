package com.example.student.service;

import com.example.student.dto.PageResponse;
import com.example.student.dto.StudentRequestDto;
import com.example.student.dto.StudentResponseDto;
import com.example.student.dto.StudentSearchRequest;

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
