package com.example.student.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.student.dto.DepartmentRequestDto;
import com.example.student.dto.DepartmentResponseDto;
import com.example.student.dto.DepartmentSearchRequest;
import com.example.student.dto.PageResponse;
import com.example.student.entity.Department;
import com.example.student.entity.DepartmentStatus;
import com.example.student.entity.StudentStatus;
import com.example.student.exception.BusinessException;
import com.example.student.exception.DuplicateDepartmentException;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.mapper.DepartmentMapper;
import com.example.student.repository.DepartmentRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.service.DepartmentService;
import com.example.student.specification.DepartmentSpecification;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;
    private final StudentRepository studentRepository;

    public DepartmentServiceImpl(DepartmentRepository repository,
                                 DepartmentMapper mapper,
                                 StudentRepository studentRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.studentRepository = studentRepository;
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

	    // 1. Fetch department from DB with ACTIVE status only
	    Department department = repository.findByIdAndStatus(id, DepartmentStatus.ACTIVE)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Department not found with id: " + id));

	    // 2. Map incoming DTO fields into existing entity (NO new object creation)
	    mapper.updateEntity(department, dto);

	    // 3. Save updated entity (ensures persistence + safe in all cases)
	    Department updated = repository.save(department);

	    // 4. Convert entity → response DTO (never expose entity to API)
	    return mapper.toDto(updated);
	}
	
	
	@Override
	public void deleteDepartment(Long id) {

	    // 1. Fetch department (ACTIVE only)
	    Department department = repository.findByIdAndStatus(id, DepartmentStatus.ACTIVE)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Department not found with id: " + id));

	    // 2. Check dependency (students exist or not)
	    long studentCount = studentRepository.countByDepartmentIdAndStatus(
	            id,
	            StudentStatus.ACTIVE
	    );

	    if (studentCount > 0) {
	        throw new BusinessException("Cannot delete department. Students are assigned to this department.");
	    }

	    // 3. Soft delete (BEST PRACTICE)
	    department.setStatus(DepartmentStatus.DELETED);

	    // 4. Save updated entity
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
