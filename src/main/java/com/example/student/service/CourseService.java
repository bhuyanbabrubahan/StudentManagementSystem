package com.example.student.service;

import com.example.student.dto.CourseRequestDto;
import com.example.student.dto.CourseResponseDto;
import com.example.student.dto.CourseSearchRequest;
import com.example.student.dto.PageResponse;

public interface CourseService {

    CourseResponseDto createCourse(CourseRequestDto dto);

    CourseResponseDto getCourseById(Long id);

    CourseResponseDto updateCourse(Long id, CourseRequestDto dto);

    void deleteCourse(Long id);

    PageResponse<CourseResponseDto> getAllCourses(int page, int size, String sortBy, String direction);

    PageResponse<CourseResponseDto> searchCourses(CourseSearchRequest request,
                                                  int page,
                                                  int size,
                                                  String sortBy,
                                                  String direction);
}
