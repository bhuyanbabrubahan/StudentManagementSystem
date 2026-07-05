package com.example.student.serviceImpl;  

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.student.dto.PageResponse;
import com.example.student.dto.StudentRequestDto;
import com.example.student.dto.StudentResponseDto;
import com.example.student.dto.StudentSearchRequest;
import com.example.student.entity.Department;
import com.example.student.entity.Student;
import com.example.student.entity.StudentStatus;
import com.example.student.exception.DuplicateStudentException;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.mapper.StudentMapper;
import com.example.student.repository.DepartmentRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentService;
import com.example.student.specification.StudentSpecification;

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
		
		if(repository.existsByEmail(requestDto.getEmail()))
		{
			throw new DuplicateStudentException("Email already exists");
		}
		
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
		Student student = repository.findByIdAndStatus(id, StudentStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException("Not Updated, Student not found with id : " + id));
		
		mapper.updateEntity(student, dto);
		
		Student updateStudent = repository.save(student) ;

		return mapper.convertToResponseDto(updateStudent);
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
	