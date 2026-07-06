package com.example.student.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.student.dto.CourseRequestDto;
import com.example.student.dto.CourseResponseDto;
import com.example.student.dto.CourseSearchRequest;
import com.example.student.dto.PageResponse;
import com.example.student.entity.Course;
import com.example.student.entity.CourseStatus;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.mapper.CourseMapper;
import com.example.student.repository.CourseRepository;
import com.example.student.service.CourseService;
import com.example.student.specification.CourseSpecification;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final CourseMapper mapper;

    public CourseServiceImpl(CourseRepository repository, CourseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // ---------------- CREATE ----------------
    @Override
    public CourseResponseDto createCourse(CourseRequestDto dto) {

        Course course = mapper.toEntity(dto);

        Course saved = repository.save(course);

        return mapper.toDto(saved);
    }

    // ---------------- GET BY ID (FIXED) ----------------
    @Override
    public CourseResponseDto getCourseById(Long id) {

        Course course = repository.findByIdAndStatusNot(id, CourseStatus.DELETED)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found with id: " + id));

        return mapper.toDto(course);
    }

    // ---------------- UPDATE (FIXED) ----------------
    @Override
    public CourseResponseDto updateCourse(Long id, CourseRequestDto dto) {

        Course course = repository.findByIdAndStatusNot(id, CourseStatus.DELETED)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found with id: " + id));

        mapper.updateEntity(course, dto);

        Course updated = repository.save(course);

        return mapper.toDto(updated);
    }

    // ---------------- DELETE (SOFT DELETE) ----------------
    @Override
    public void deleteCourse(Long id) {

        Course course = repository.findByIdAndStatusNot(id, CourseStatus.DELETED)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found with id: " + id));

        course.setStatus(CourseStatus.DELETED);

        repository.save(course);
    }

    // ---------------- GET ALL (FIXED FILTER) ----------------
    @Override
    public PageResponse<CourseResponseDto> getAllCourses(int page,
                                                          int size,
                                                          String sortBy,
                                                          String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // 🔥 FIX: exclude deleted
        Page<Course> coursePage = repository.findByStatusNot(CourseStatus.DELETED, pageable);

        List<CourseResponseDto> content = coursePage.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();

        PageResponse<CourseResponseDto> response = new PageResponse<>();

        response.setContent(content);
        response.setPageNumber(coursePage.getNumber());
        response.setPageSize(coursePage.getSize());
        response.setTotalElements(coursePage.getTotalElements());
        response.setTotalPages(coursePage.getTotalPages());
        response.setLast(coursePage.isLast());

        return response;
    }

    // ---------------- SEARCH (PRODUCTION OPTIMIZED) ----------------
    @Override
    public PageResponse<CourseResponseDto> searchCourses(CourseSearchRequest request,
                                                         int page,
                                                         int size,
                                                         String sortBy,
                                                         String direction) {

        Specification<Course> spec = Specification.where(null);

        // 🔥 DEFAULT ACTIVE FILTER
        if (request.getStatus() != null) {
            spec = spec.and(CourseSpecification.hasStatus(request.getStatus()));
        } else {
            spec = spec.and(CourseSpecification.hasStatus(CourseStatus.ACTIVE));
        }

        // COURSE NAME
        if (request.getCourseName() != null && !request.getCourseName().isBlank()) {
            spec = spec.and(CourseSpecification.hasCourseName(request.getCourseName()));
        }

        // COURSE CODE
        if (request.getCourseCode() != null && !request.getCourseCode().isBlank()) {
            spec = spec.and(CourseSpecification.hasCourseCode(request.getCourseCode()));
        }

        // DEPARTMENT
        if (request.getDepartmentId() != null) {
            spec = spec.and(CourseSpecification.hasDepartment(request.getDepartmentId()));
        }

		// FEES RANGE (CORRECT LOGIC)
		if (request.getMinFees() != null && request.getMaxFees() != null) {

			spec = spec.and(CourseSpecification.hasFeesGreaterThan(request.getMinFees())
					.and(CourseSpecification.hasFeesLessThan(request.getMaxFees())));

		} else {

			if (request.getMinFees() != null) {
				spec = spec.and(CourseSpecification.hasFeesGreaterThan(request.getMinFees()));
			}

			if (request.getMaxFees() != null) {
				spec = spec.and(CourseSpecification.hasFeesLessThan(request.getMaxFees()));
			}
		}

        // SORT
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Course> coursePage = repository.findAll(spec, pageable);

        List<CourseResponseDto> content = coursePage.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();

        PageResponse<CourseResponseDto> response = new PageResponse<>();

        response.setContent(content);
        response.setPageNumber(coursePage.getNumber());
        response.setPageSize(coursePage.getSize());
        response.setTotalElements(coursePage.getTotalElements());
        response.setTotalPages(coursePage.getTotalPages());
        response.setLast(coursePage.isLast());

        return response;
    }
}