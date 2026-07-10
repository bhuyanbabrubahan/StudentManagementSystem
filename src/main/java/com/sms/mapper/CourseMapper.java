package com.sms.mapper;

import org.springframework.stereotype.Component;

import com.sms.dto.CourseRequestDto;
import com.sms.dto.CourseResponseDto;
import com.sms.entity.Course;
import com.sms.entity.Department;
import com.sms.enums.CourseStatus;
import com.sms.exception.ResourceNotFoundException;
import com.sms.repository.DepartmentRepository;

@Component
public class CourseMapper {

    private final DepartmentRepository departmentRepository;

    public CourseMapper(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // 1. DTO → ENTITY (CREATE)
    public Course toEntity(CourseRequestDto dto) {

        Course course = new Course();

        course.setCourseCode(dto.getCourseCode());
        course.setCourseName(dto.getCourseName());
        course.setDurationInMonths(dto.getDurationInMonths());
        course.setFees(dto.getFees());
        course.setDescription(dto.getDescription());
        course.setStatus(dto.getStatus() != null ? dto.getStatus() : CourseStatus.ACTIVE);

        // 🔥 Department mapping (only reference set)
        Department department = departmentRepository
                .findById(dto.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        course.setDepartment(department);

        return course;
    }

    // 2. ENTITY → RESPONSE DTO
    public CourseResponseDto toDto(Course course) {

        CourseResponseDto dto = new CourseResponseDto();

        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseName(course.getCourseName());
        dto.setDurationInMonths(course.getDurationInMonths());
        dto.setFees(course.getFees());
        dto.setDescription(course.getDescription());
        dto.setStatus(course.getStatus());

        if (course.getDepartment() != null) {
            dto.setDepartmentId(course.getDepartment().getId());
            dto.setDepartmentName(course.getDepartment().getDepartmentName());
        }

        dto.setCreatedAt(course.getCreatedAt());
        dto.setUpdatedAt(course.getUpdatedAt());

        return dto;
    }

    // 3. UPDATE ENTITY (DIRTY CHECKING POWER)
    public void updateEntity(Course course, CourseRequestDto dto) {

        course.setCourseCode(dto.getCourseCode());
        course.setCourseName(dto.getCourseName());
        course.setDurationInMonths(dto.getDurationInMonths());
        course.setFees(dto.getFees());
        course.setDescription(dto.getDescription());
        course.setStatus(dto.getStatus());

        // 🔥 Department update (if changed)
        if (dto.getDepartmentId() != null &&
            (course.getDepartment() == null ||
             !dto.getDepartmentId().equals(course.getDepartment().getId()))) {

            Department department = departmentRepository
                    .findById(dto.getDepartmentId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Department not found"));

            course.setDepartment(department);
        }
    }
}