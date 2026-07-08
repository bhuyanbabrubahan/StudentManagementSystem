package com.sms.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sms.dto.DepartmentRequestDto;
import com.sms.dto.DepartmentResponseDto;
import com.sms.dto.DepartmentSearchRequest;
import com.sms.dto.PageResponse;
import com.sms.entity.Department;
import com.sms.entity.DepartmentStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.DuplicateDepartmentException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.mapper.DepartmentMapper;
import com.sms.repository.CourseRepository;
import com.sms.repository.DepartmentRepository;
import com.sms.repository.StudentRepository;
import com.sms.service.DepartmentService;
import com.sms.specification.DepartmentSpecification;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public DepartmentServiceImpl(DepartmentRepository repository,
                                 DepartmentMapper mapper,
                                 StudentRepository studentRepository,
                                 CourseRepository courseRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto dto) {

        // 1. Duplicate check (business rule)
        if (repository.existsByDepartmentCode(dto.getDepartmentCode())) {
            throw new DuplicateDepartmentException("Department code already exists");
        }

        // 2. Convert DTO → Entity
        Department department = mapper.toEntity(dto);

        // 3. Default status set (VERY IMPORTANT in production)
        department.setStatus(DepartmentStatus.ACTIVE);

        // 4. Save to DB
        Department saved = repository.save(department);

        // 5. Convert Entity → Response DTO
        return mapper.toDto(saved);
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {

        Department department = repository.findByIdAndStatus(id, DepartmentStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: " + id));

        return mapper.toDto(department);
    }
	
    @Override
    public PageResponse<DepartmentResponseDto> getAllDepartments(
            int page,
            int size,
            String sortBy,
            String direction) {

        // 1. Sorting logic (ASC / DESC)
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // 2. Pageable object (pagination setup)
        Pageable pageable = PageRequest.of(page, size, sort);

        // 3. Fetch only ACTIVE departments
        Page<Department> departmentPage =
                repository.findByStatus(DepartmentStatus.ACTIVE, pageable);

        // 4. Convert Entity → DTO
        List<DepartmentResponseDto> content = departmentPage.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();

        // 5. Wrap response in PageResponse
        PageResponse<DepartmentResponseDto> response = new PageResponse<>();

        response.setContent(content);
        response.setPageNumber(departmentPage.getNumber());
        response.setPageSize(departmentPage.getSize());
        response.setTotalElements(departmentPage.getTotalElements());
        response.setTotalPages(departmentPage.getTotalPages());
        response.setLast(departmentPage.isLast());

        return response;
    }
	
	
    @Override
    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto dto) {

        // 1. Fetch ACTIVE department from database
        Department department = repository.findByIdAndStatus(id, DepartmentStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: " + id));

        // 2. Check duplicate department name (ignore current record)
        if (repository.existsByDepartmentNameAndIdNot(dto.getDepartmentName(), id)) {
            throw new BusinessException("Department name already exists.");
        }

        // 3. Check duplicate department code (ignore current record)
        if (repository.existsByDepartmentCodeAndIdNot(dto.getDepartmentCode(), id)) {
            throw new BusinessException("Department code already exists.");
        }

        // 4. Copy DTO values into managed entity (Dirty Checking)
        mapper.updateEntity(department, dto);

        // 5. Save updated entity
        Department updatedDepartment = repository.save(department);

        // 6. Convert Entity -> Response DTO
        return mapper.toDto(updatedDepartment);
    }
	
	
    @Override
    public void deleteDepartment(Long id) {

        // 1. Fetch ACTIVE department
        Department department = repository.findByIdAndStatus(id, DepartmentStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + id));

        // 2. Count dependencies (Students)
        long studentCount = studentRepository.countByDepartment_Id(id);

        // 3. Count dependencies (Courses)
        long courseCount = courseRepository.countByDepartment_Id(id);

        // 4. Business validation
        if (studentCount > 0 || courseCount > 0) {

            throw new BusinessException(
                    "Cannot delete Department. Students = "
                            + studentCount +
                            ", Courses = " + courseCount
            );
        }

        // 5. Soft delete
        department.setStatus(DepartmentStatus.DELETED);

        repository.save(department);
    }
	
	
	@Override
	public PageResponse<DepartmentResponseDto> searchDepartments(
	        DepartmentSearchRequest request,
	        int page,
	        int size,
	        String sortBy,
	        String direction) {

	    // 1. Base specification
	    Specification<Department> spec;

	    // 2. Default ACTIVE filter
	    if (request.getStatus() != null) {
	        spec = Specification.where(
	                DepartmentSpecification.hasStatus(request.getStatus()));
	    } else {
	        spec = Specification.where(
	                DepartmentSpecification.hasStatus(DepartmentStatus.ACTIVE));
	    }

	    // 3. Dynamic filters
	    if (request.getDepartmentName() != null) {
	        spec = spec.and(
	                DepartmentSpecification.hasDepartmentName(request.getDepartmentName()));
	    }

	    if (request.getDepartmentCode() != null) {
	        spec = spec.and(
	                DepartmentSpecification.hasDepartmentCode(request.getDepartmentCode()));
	    }

	    // 4. Sorting
	    Sort sort = direction.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    // 5. Query execution
	    Page<Department> departmentPage =
	            repository.findAll(spec, pageable);

	    // 6. Mapping
	    List<DepartmentResponseDto> content = departmentPage.getContent()
	            .stream()
	            .map(mapper::toDto)
	            .toList();

	    // 7. Response build
	    PageResponse<DepartmentResponseDto> response = new PageResponse<>();

	    response.setContent(content);
	    response.setPageNumber(departmentPage.getNumber());
	    response.setPageSize(departmentPage.getSize());
	    response.setTotalElements(departmentPage.getTotalElements());
	    response.setTotalPages(departmentPage.getTotalPages());
	    response.setLast(departmentPage.isLast());

	    return response;
	}
	
	
	
	
	
}
