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
import com.example.student.entity.Department;
import com.example.student.entity.DepartmentStatus;
import com.example.student.exception.BusinessException;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.mapper.CourseMapper;
import com.example.student.repository.CourseRepository;
import com.example.student.repository.DepartmentRepository;
import com.example.student.service.CourseService;
import com.example.student.specification.CourseSpecification;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final CourseMapper mapper;
    private final DepartmentRepository departmentRepository;

    public CourseServiceImpl(
            CourseRepository repository,
            CourseMapper mapper,
            DepartmentRepository departmentRepository) {

        this.repository = repository;
        this.mapper = mapper;
        this.departmentRepository = departmentRepository;
    }

    // ---------------- CREATE ----------------
    @Override
    public CourseResponseDto createCourse(CourseRequestDto dto) {

        // 1. Check duplicate course name
        if (repository.existsByCourseName(dto.getCourseName())) {
            throw new BusinessException("Course name already exists.");
        }

        // 2. Check duplicate course code
        if (repository.existsByCourseCode(dto.getCourseCode())) {
            throw new BusinessException("Course code already exists.");
        }

        // 3. Fetch ACTIVE department
        Department department = departmentRepository
                .findByIdAndStatus(dto.getDepartmentId(), DepartmentStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: "
                                        + dto.getDepartmentId()));

        // 4. Convert DTO → Entity
        Course course = mapper.toEntity(dto);

        // 5. Set relationship
        course.setDepartment(department);

        // 6. Save entity
        Course saved = repository.save(course);

        // 7. Return Response DTO
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

        // 1. Fetch existing ACTIVE course
        Course course = repository.findByIdAndStatusNot(id, CourseStatus.DELETED)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found with id: " + id));

        // 2. Check duplicate course name (ignore current course)
        if (repository.existsByCourseNameAndIdNot(dto.getCourseName(), id)) {
            throw new BusinessException("Course name already exists.");
        }

        // 3. Check duplicate course code (ignore current course)
        if (repository.existsByCourseCodeAndIdNot(dto.getCourseCode(), id)) {
            throw new BusinessException("Course code already exists.");
        }

        // 4. Fetch ACTIVE department
        Department department = departmentRepository
                .findByIdAndStatus(dto.getDepartmentId(), DepartmentStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + dto.getDepartmentId()));

        // 5. Update simple fields (Dirty Checking)
        mapper.updateEntity(course, dto);

        // 6. Update relationship if department changed
        course.setDepartment(department);

        // 7. Save updated course
        Course updated = repository.save(course);

        // 8. Return response DTO
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