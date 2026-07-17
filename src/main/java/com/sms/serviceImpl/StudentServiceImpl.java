package com.sms.serviceImpl;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.location.address.entity.Address;
import com.location.address.service.AddressService;
import com.sms.dto.PageResponse;
import com.sms.dto.StudentRequestDto;
import com.sms.dto.StudentResponseDto;
import com.sms.dto.StudentSearchRequest;
import com.sms.entity.Department;
import com.sms.entity.Student;
import com.sms.enums.RecordStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.ResourceAlreadyExistsException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.mapper.StudentMapper;
import com.sms.repository.DepartmentRepository;
import com.sms.repository.StudentRepository;
import com.sms.security.entity.Role;
import com.sms.security.entity.User;
import com.sms.security.entity.UserStatus;
import com.sms.security.repository.UserRepository;
import com.sms.sequence.enums.ModuleType;
import com.sms.sequence.service.CodeGeneratorService;
import com.sms.service.StudentService;
import com.sms.specification.StudentSpecification;
import com.sms.util.PageValidator;
import com.sms.util.SortFieldValidator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository repository;
	private final DepartmentRepository departmentRepository;
	private final StudentMapper mapper;
	private final CodeGeneratorService codeGeneratorService;
	private final AddressService addressService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public StudentResponseDto saveStudent(StudentRequestDto requestDto) {

	    // ==========================================================
	    // Validate Duplicate Phone Number
	    // ==========================================================

	    if (repository.existsByPhoneNumber(requestDto.getPhoneNumber())) {

	        throw new BusinessException(
	                "Phone number already exists."
	        );

	    }

	    // ==========================================================
	    // Validate Duplicate Email
	    // ==========================================================

	    if (userRepository.existsByEmail(requestDto.getEmail())) {

	        throw new ResourceAlreadyExistsException(
	                "Email already exists : " + requestDto.getEmail()
	        );

	    }

	    // ==========================================================
	    // Validate Department
	    // ==========================================================

	    Department department = departmentRepository
	            .findByIdAndStatus(
	                    requestDto.getDepartmentId(),
	                    RecordStatus.ACTIVE
	            )
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Department not found."
	                    )
	            );

	    // ==========================================================
	    // Create Address
	    // ==========================================================

	    Address address = addressService.saveAddress(
	            requestDto.getAddress()
	    );

	    // ==========================================================
	    // Create User
	    // ==========================================================

	    User user = new User();

	    user.setEmail(
	            requestDto.getEmail().trim()
	    );

	    user.setPassword(
	            passwordEncoder.encode(
	                    requestDto.getPassword()
	            )
	    );

	    user.setRole(
	            Role.STUDENT
	    );

	    user.setStatus(
	            UserStatus.ACTIVE
	    );

	    user = userRepository.save(user);

	    // ==========================================================
	    // DTO -> Entity
	    // ==========================================================

	    Student student = mapper.convertToEntity(
	            requestDto
	    );

	    // ==========================================================
	    // Business Fields
	    // ==========================================================

	    student.setRollNumber(
	            codeGeneratorService.generateCode(
	                    ModuleType.STUDENT,
	                    "STU"
	            )
	    );

	    student.setStatus(
	            RecordStatus.ACTIVE
	    );

	    // ==========================================================
	    // Relationships
	    // ==========================================================

	    student.setDepartment(
	            department
	    );

	    student.setAddress(
	            address
	    );

	    student.setUser(
	            user
	    );

	    // ==========================================================
	    // Save Student
	    // ==========================================================

	    Student savedStudent = repository.save(
	            student
	    );

	    // ==========================================================
	    // Response
	    // ==========================================================

	    return mapper.convertToResponseDto(
	            savedStudent
	    );

	}
	
	
	

	@Override
	@Transactional(readOnly = true)
	public StudentResponseDto getStudentById(Long id) {

	    Student student = repository
	            .findByIdAndStatus(id, RecordStatus.ACTIVE)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Student not found with id : " + id
	                    ));

	    return mapper.convertToResponseDto(student);
	}

	
	@Override
	@Transactional
	public StudentResponseDto updateStudent(
	        Long id,
	        StudentRequestDto requestDto
	) {

	    // ==========================================================
	    // Validate Student
	    // ==========================================================

	    Student student = repository
	            .findByIdAndStatus(id, RecordStatus.ACTIVE)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Student not found with id : " + id
	                    )
	            );

	    // ==========================================================
	    // Validate Duplicate Phone Number
	    // ==========================================================

	    if (!student.getPhoneNumber().equals(requestDto.getPhoneNumber())
	            && repository.existsByPhoneNumber(requestDto.getPhoneNumber())) {

	        throw new BusinessException(
	                "Phone number already exists."
	        );

	    }

	    // ==========================================================
	    // Validate Department
	    // ==========================================================

	    Department department = departmentRepository
	            .findByIdAndStatus(
	                    requestDto.getDepartmentId(),
	                    RecordStatus.ACTIVE
	            )
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Department not found."
	                    )
	            );

	    // ==========================================================
	    // Update Student Basic Details
	    // ==========================================================

	    mapper.updateEntity(
	            student,
	            requestDto
	    );

	    // ==========================================================
	    // Update Department
	    // ==========================================================

	    student.setDepartment(
	            department
	    );

	    // ==========================================================
	    // Update Address
	    // ==========================================================

	    Address address = student.getAddress();

	    if (address == null) {

	        address = addressService.saveAddress(
	                requestDto.getAddress()
	        );

	    } else {

	        address = addressService.updateAddress(
	                address,
	                requestDto.getAddress()
	        );

	    }

	    student.setAddress(
	            address
	    );

	    // ==========================================================
	    // Update User
	    // ==========================================================

	    User user = student.getUser();

	    if (user == null) {

	        throw new ResourceNotFoundException(
	                "User not found for this student."
	        );

	    }

	    // ==========================================================
	    // Validate Duplicate Email
	    // ==========================================================

	    if (!user.getEmail().equalsIgnoreCase(requestDto.getEmail())
	            && userRepository.existsByEmail(requestDto.getEmail())) {

	        throw new ResourceAlreadyExistsException(
	                "Email already exists : " + requestDto.getEmail()
	        );

	    }

	    user.setEmail(
	            requestDto.getEmail().trim()
	    );

	    if (requestDto.getPassword() != null
	            && !requestDto.getPassword().isBlank()) {

	        user.setPassword(
	                passwordEncoder.encode(
	                        requestDto.getPassword()
	                )
	        );

	    }

	    userRepository.save(user);

	    // ==========================================================
	    // Save Student
	    // ==========================================================

	    Student updatedStudent = repository.save(
	            student
	    );

	    // ==========================================================
	    // Response
	    // ==========================================================

	    return mapper.convertToResponseDto(
	            updatedStudent
	    );

	}
	
	
	
	
	
	@Override
	@Transactional
	public void deleteStudent(Long id) {

	    // ==========================================================
	    // Validate Student
	    // ==========================================================

	    Student student = repository
	            .findByIdAndStatus(id, RecordStatus.ACTIVE)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Student not found with id : " + id
	                    )
	            );

	    // ==========================================================
	    // Soft Delete Student
	    // ==========================================================

	    student.setStatus(
	            RecordStatus.DELETED
	    );

	    // ==========================================================
	    // Disable User Login
	    // ==========================================================

	    User user = student.getUser();

	    if (user != null) {

	        user.setStatus(
	                UserStatus.INACTIVE
	        );

	        userRepository.save(user);

	    }

	    // ==========================================================
	    // Save Student
	    // ==========================================================

	    repository.save(student);

	}
	
	

	
	
	
	@Override
	@Transactional(readOnly = true)
	public PageResponse<StudentResponseDto> getAllStudents(
	        int page,
	        int size,
	        String sortBy,
	        String direction
	) {
		
		PageValidator.validate(page, size);

		SortFieldValidator.validate(
		        sortBy,
		        ALLOWED_SORT_FIELDS
		);

	    Sort sort = direction.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    Page<Student> studentPage =
	            repository.findByStatus(
	                    RecordStatus.ACTIVE,
	                    pageable
	            );

	    return new PageResponse<>(

	            studentPage.getContent()
	                    .stream()
	                    .map(mapper::convertToResponseDto)
	                    .toList(),

	            studentPage.getNumber(),

	            studentPage.getSize(),

	            studentPage.getTotalElements(),

	            studentPage.getTotalPages(),

	            studentPage.isLast()

	    );
	}
	
	
	

	@Override
	@Transactional(readOnly = true)
	public PageResponse<StudentResponseDto> searchStudents(
	        StudentSearchRequest request,
	        int page,
	        int size,
	        String sortBy,
	        String direction
	) {
		
		PageValidator.validate(page, size);

		SortFieldValidator.validate(
		        sortBy,
		        ALLOWED_SORT_FIELDS
		);

	    // ==========================================================
	    // Validation
	    // ==========================================================

	    if (request.getAdmissionDateFrom() != null
	            && request.getAdmissionDateTo() != null
	            && request.getAdmissionDateFrom().isAfter(request.getAdmissionDateTo())) {

	        throw new BusinessException(
	                "Admission Date From cannot be after Admission Date To."
	        );
	    }

	    if (request.getDateOfBirthFrom() != null
	            && request.getDateOfBirthTo() != null
	            && request.getDateOfBirthFrom().isAfter(request.getDateOfBirthTo())) {

	        throw new BusinessException(
	                "Date Of Birth From cannot be after Date Of Birth To."
	        );
	    }

	    if (request.getMinFees() != null
	            && request.getMaxFees() != null
	            && request.getMinFees().compareTo(request.getMaxFees()) > 0) {

	        throw new BusinessException(
	                "Minimum Fees cannot be greater than Maximum Fees."
	        );
	    }

	    // ==========================================================
	    // Build Specification
	    // ==========================================================

	    Specification<Student> specification = Specification
	            .where(StudentSpecification.hasId(request.getId()))
	            .and(StudentSpecification.hasRollNumber(request.getRollNumber()))
	            .and(StudentSpecification.hasFirstName(request.getFirstName()))
	            .and(StudentSpecification.hasLastName(request.getLastName()))
	            .and(StudentSpecification.hasFullName(request.getFullName()))
	            .and(StudentSpecification.hasPhoneNumber(request.getPhoneNumber()))
	            .and(StudentSpecification.hasDepartmentId(request.getDepartmentId()))
	            .and(StudentSpecification.hasGender(request.getGender()))
	            .and(StudentSpecification.hasVillage(request.getVillage()))
	            .and(StudentSpecification.hasTehsil(request.getTehsil()))
	            .and(StudentSpecification.hasDistrict(request.getDistrict()))
	            .and(StudentSpecification.hasState(request.getState()))
	            .and(StudentSpecification.hasCountry(request.getCountry()))
	            .and(StudentSpecification.hasPincode(request.getPincode()))
	            .and(StudentSpecification.admissionDateFrom(request.getAdmissionDateFrom()))
	            .and(StudentSpecification.admissionDateTo(request.getAdmissionDateTo()))
	            .and(StudentSpecification.dateOfBirthFrom(request.getDateOfBirthFrom()))
	            .and(StudentSpecification.dateOfBirthTo(request.getDateOfBirthTo()))
	            .and(StudentSpecification.minFees(request.getMinFees()))
	            .and(StudentSpecification.maxFees(request.getMaxFees()));

	    // ==========================================================
	    // Default Status = ACTIVE
	    // ==========================================================

	    if (request.getStatus() != null) {
	        specification = specification.and(
	                StudentSpecification.hasStatus(request.getStatus())
	        );
	    } else {
	        specification = specification.and(
	                StudentSpecification.hasStatus(RecordStatus.ACTIVE)
	        );
	    }

	    // ==========================================================
	    // Sorting
	    // ==========================================================

	    Sort sort = direction.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    // ==========================================================
	    // Execute Query
	    // ==========================================================

	    Page<Student> studentPage =
	            repository.findAll(specification, pageable);

	    // ==========================================================
	    // Response
	    // ==========================================================

	    return new PageResponse<>(

	            studentPage.getContent()
	                    .stream()
	                    .map(mapper::convertToResponseDto)
	                    .toList(),

	            studentPage.getNumber(),

	            studentPage.getSize(),

	            studentPage.getTotalElements(),

	            studentPage.getTotalPages(),

	            studentPage.isLast()

	    );
	}
	
	
	private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(

			"id",

			"rollNumber",

			"firstName",

			"lastName",

			"phoneNumber",

			"fees",

			"admissionDate",

			"dateOfBirth",

			"createdAt",

			"updatedAt"

	);

}
