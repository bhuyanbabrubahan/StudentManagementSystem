package com.sms.serviceImpl;  

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sms.dto.PageResponse;
import com.sms.dto.StudentRequestDto;
import com.sms.dto.StudentResponseDto;
import com.sms.dto.StudentSearchRequest;
import com.sms.entity.Department;
import com.sms.entity.Student;
import com.sms.entity.StudentStatus;
import com.sms.exception.ResourceNotFoundException;
import com.sms.mapper.StudentMapper;
import com.sms.repository.DepartmentRepository;
import com.sms.repository.StudentRepository;
import com.sms.service.StudentService;
import com.sms.specification.StudentSpecification;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository repository;

	private final StudentMapper mapper;
	
	private final DepartmentRepository departmentRepository;

	public StudentServiceImpl(StudentRepository repository, StudentMapper mapper, DepartmentRepository departmentRepository) {

		this.repository = repository;
		this.mapper = mapper;
		this.departmentRepository =  departmentRepository;

	}

	@Override
	public StudentResponseDto saveStudent(StudentRequestDto requestDto) {
		
		Department department = departmentRepository
		        .findById(requestDto.getDepartmentId())
		        .orElseThrow(() ->
		                new ResourceNotFoundException("Department not found"));

		Student student = 
				mapper.convertToEntity(requestDto); //Request DTO to make Entity
		
		student.setDepartment(department); //Only object reference is set, no DB operation
		
		Student savedStudent = 
				repository.save(student); //We cant save repo we have to map first then save student entity
		
		StudentResponseDto responseDto = 
				mapper.convertToResponseDto(savedStudent);
		
		return responseDto;
	}
	
	
	@Override
	public StudentResponseDto getStudentById(Long id) {

	    Student student = repository.findByIdAndStatus(id, StudentStatus.ACTIVE)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Student not found with id : " + id));

	    return mapper.convertToResponseDto(student);

	}
	
	
	
	

	@Override
	public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {

	    // 1. Fetch existing active student
	    Student student = repository.findByIdAndStatus(id, StudentStatus.ACTIVE)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Student not found with id : " + id));

	    // 2. Validate department
	    Department department = departmentRepository
	            .findById(dto.getDepartmentId())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Department not found"));

	    // 3. Update student fields
	    mapper.updateEntity(student, dto);

	    // 4. Update relationship
	    student.setDepartment(department);

	    // 5. Save
	    Student updatedStudent = repository.save(student);

	    // 6. Return DTO
	    return mapper.convertToResponseDto(updatedStudent);
	}
	
	
	
	@Override
	public void deleteStudent(Long id) {
		Student student = repository.findByIdAndStatus(id, StudentStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + id));
		
		student.setStatus(StudentStatus.DELETED);
		repository.save(student);
	}

	

	@Override
	public PageResponse<StudentResponseDto> getAllStudents(int page, int size, String sortBy, String direction) {
		
		Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Student> studentPage =
	            repository.findByStatus(StudentStatus.ACTIVE, pageable);

	    List<StudentResponseDto> content = studentPage.getContent()
	            .stream()
	            .map(mapper::convertToResponseDto)
	            .toList();

	    PageResponse<StudentResponseDto> response = new PageResponse<>();
	    
	    
	    response.setContent(content);
	    response.setPageNumber(studentPage.getNumber());
	    response.setPageSize(studentPage.getSize());
	    response.setTotalElements(studentPage.getTotalElements());
	    response.setTotalPages(studentPage.getTotalPages());
	    response.setLast(studentPage.isLast());
	    
	    
		return response;

	    
	}


	
	@Override
	public PageResponse<StudentResponseDto> searchStudents(
	        StudentSearchRequest request,
	        int page,
	        int size,
	        String sortBy,
	        String direction) {

	    Specification<Student> spec;

	    // Default ACTIVE, unless user explicitly asks for another status
	    if (request.getStatus() != null) {
	        spec = Specification.where(
	                StudentSpecification.hasStatus(request.getStatus()));
	    } else {
	        spec = Specification.where(
	                StudentSpecification.hasStatus(StudentStatus.ACTIVE));
	    }

	    if (request.getGender() != null) {
	        spec = spec.and(
	                StudentSpecification.hasGender(request.getGender()));
	    }

	    if (request.getFees() != null) {
	        spec = spec.and(
	                StudentSpecification.hasFeesGreaterThan(request.getFees()));
	    }

	    Sort sort = direction.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Student> studentPage = repository.findAll(spec, pageable);

	    PageResponse<StudentResponseDto> response = new PageResponse<>();

	    response.setContent(
	            studentPage.getContent()
	                    .stream()
	                    .map(mapper::convertToResponseDto)
	                    .toList());

	    response.setPageNumber(studentPage.getNumber());
	    response.setPageSize(studentPage.getSize());
	    response.setTotalPages(studentPage.getTotalPages());
	    response.setTotalElements(studentPage.getTotalElements());
	    response.setLast(studentPage.isLast());

	    return response;
	}
	

	

}
	