package com.sms.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.location.address.entity.Address;
import com.location.address.repository.AddressRepository;
import com.location.village.repository.VillageRepository;
import com.location.village.entity.Village;

import com.sms.dto.FacultyRequestDto;
import com.sms.dto.FacultyResponseDto;
import com.sms.dto.FacultySearchRequest;
import com.sms.dto.PageResponse;
import com.sms.entity.Department;
import com.sms.entity.Faculty;
import com.sms.enums.DepartmentStatus;
import com.sms.enums.FacultyStatus;
import com.sms.exception.ResourceAlreadyExistsException;
import com.sms.exception.ResourceNotFoundException;

import com.sms.mapper.FacultyMapper;

import com.sms.repository.DepartmentRepository;
import com.sms.repository.FacultyRepository;
import com.sms.security.entity.Role;
import com.sms.security.entity.User;
import com.sms.security.entity.UserStatus;
import com.sms.security.repository.UserRepository;

import com.sms.sequence.enums.ModuleType;
import com.sms.sequence.service.CodeGeneratorService;

import com.sms.service.FacultyService;
import com.sms.specification.FacultySpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class FacultyServiceImpl implements FacultyService {

	private final FacultyRepository facultyRepository;
	private final UserRepository userRepository;
	private final DepartmentRepository departmentRepository;
	private final VillageRepository villageRepository;
	private final AddressRepository addressRepository;
	private final PasswordEncoder passwordEncoder;
	private final FacultyMapper mapper;
	private final CodeGeneratorService codeGeneratorService;

	public FacultyServiceImpl(

			FacultyRepository facultyRepository, UserRepository userRepository,
			DepartmentRepository departmentRepository, VillageRepository villageRepository,
			AddressRepository addressRepository, PasswordEncoder passwordEncoder, FacultyMapper mapper,
			CodeGeneratorService codeGeneratorService) {

		this.facultyRepository = facultyRepository;
		this.userRepository = userRepository;
		this.departmentRepository = departmentRepository;
		this.villageRepository = villageRepository;
		this.addressRepository = addressRepository;
		this.passwordEncoder = passwordEncoder;
		this.mapper = mapper;
		this.codeGeneratorService = codeGeneratorService;

	}

	@Override
	@Transactional
	public FacultyResponseDto createFaculty(FacultyRequestDto request) {

		// 1. Check Email already exists

		if (userRepository.existsByEmail(request.getEmail())) {

			throw new ResourceAlreadyExistsException("Email already exists : " + request.getEmail());

		}

		// 2. Validate Department

		Department department =

				departmentRepository.findByIdAndStatus(request.getDepartmentId(), DepartmentStatus.ACTIVE)

						.orElseThrow(() ->

						new ResourceNotFoundException("Department not found or inactive")

						);

		// 3. Create User

		User user = new User();

		user.setEmail(request.getEmail());

		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user.setRole(Role.FACULTY);

		user.setStatus(UserStatus.ACTIVE);

		user = userRepository.save(user);

		// 4. Create Address

		Village village =

				villageRepository.findById(request.getAddress().getVillageId())

						.orElseThrow(() ->

						new ResourceNotFoundException("Village not found")

						);

		Address address = new Address();

		address.setHouseNumber(request.getAddress().getHouseNumber());

		address.setStreet(request.getAddress().getStreet());

		address.setLandmark(request.getAddress().getLandmark());

		address.setPostalCode(request.getAddress().getPostalCode());

		address.setAddressType(request.getAddress().getAddressType());

		address.setVillage(village);

		address = addressRepository.save(address);

		// 5. Create Faculty Entity

		Faculty faculty =

				mapper.convertToEntity(request);

		faculty.setDepartment(department);

		faculty.setAddress(address);

		faculty.setUser(user);

		// 6. Generate Employee Code

		faculty.setEmployeeCode(

				codeGeneratorService.generateCode(ModuleType.FACULTY, "FAC")

		);

		faculty.setStatus(FacultyStatus.ACTIVE);

		// 7. Save Faculty

		Faculty savedFaculty =

				facultyRepository.save(faculty);

		// 8. Return Response

		return mapper.convertToResponseDto(savedFaculty);

	}

	@Override
	@Transactional(readOnly = true)
	public FacultyResponseDto getFacultyById(Long id) {

	    Faculty faculty =
	        facultyRepository.findByIdAndStatus(
	                id,
	                FacultyStatus.ACTIVE
	        )
	        .orElseThrow(
	             () -> new ResourceNotFoundException("Faculty not found")
	        );


	    return mapper.convertToResponseDto(faculty);
	}

	@Override
	public PageResponse<FacultyResponseDto> getAllFaculties(
	        int page,
	        int size,
	        String sortBy,
	        String direction) {


	    Sort sort =
	            direction.equalsIgnoreCase("asc")
	            ?
	            Sort.by(sortBy).ascending()
	            :
	            Sort.by(sortBy).descending();



	    Pageable pageable =
	            PageRequest.of(
	                    page,
	                    size,
	                    sort
	            );


	    Page<Faculty> facultyPage =
	            facultyRepository
	            .findByStatus(
	                    FacultyStatus.ACTIVE,
	                    pageable
	            );


	    PageResponse<FacultyResponseDto> response =
	            new PageResponse<>();


	    response.setContent(
	            facultyPage
	            .getContent()
	            .stream()
	            .map(mapper::convertToResponseDto)
	            .toList()
	    );


	    response.setPageNumber(
	            facultyPage.getNumber()
	    );


	    response.setPageSize(
	            facultyPage.getSize()
	    );


	    response.setTotalElements(
	            facultyPage.getTotalElements()
	    );


	    response.setTotalPages(
	            facultyPage.getTotalPages()
	    );


	    response.setLast(
	            facultyPage.isLast()
	    );


	    return response;

	}

	@Override
	@Transactional
	public FacultyResponseDto updateFaculty(
	        Long id,
	        FacultyRequestDto request) {


	    Faculty faculty =

	            facultyRepository
	            .findByIdAndStatus(
	                    id,
	                    FacultyStatus.ACTIVE
	            )

	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Faculty not found"
	                    )
	            );



	    Department department =

	            departmentRepository
	            .findByIdAndStatus(
	                    request.getDepartmentId(),
	                    DepartmentStatus.ACTIVE
	            )

	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Department not found"
	                    )
	            );



	    mapper.updateEntity(
	            faculty,
	            request
	    );


	    faculty.setDepartment(
	            department
	    );


	    Faculty updated =

	            facultyRepository.save(
	                    faculty
	            );


	    return mapper.convertToResponseDto(
	            updated
	    );

	}

	@Override
	@Transactional
	public void deleteFaculty(Long id) {


	    Faculty faculty =

	            facultyRepository
	            .findByIdAndStatus(
	                    id,
	                    FacultyStatus.ACTIVE
	            )

	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Faculty not found"
	                    )
	            );


	    faculty.setStatus(
	            FacultyStatus.DELETED
	    );


	    facultyRepository.save(
	            faculty
	    );

	}

	@Override
	public PageResponse<FacultyResponseDto> searchFaculties(
	        FacultySearchRequest request,
	        int page,
	        int size,
	        String sortBy,
	        String direction) {



	    Specification<Faculty> spec =
	            Specification.where(null);

	    if(request.getId()!=null){

	        spec = spec.and(
	                FacultySpecification.hasId(
	                        request.getId()
	                )
	        );

	    }

	    // Default ACTIVE faculty

	    if(request.getStatus()!=null){

	        spec = spec.and(
	                FacultySpecification.hasStatus(
	                        request.getStatus()
	                )
	        );

	    }
	    else{

	        spec = spec.and(
	                FacultySpecification.hasStatus(
	                        FacultyStatus.ACTIVE
	                )
	        );

	    }



	    if(request.getFirstName()!=null &&
	            !request.getFirstName().isBlank()){

	        spec = spec.and(
	                FacultySpecification.hasFirstName(
	                        request.getFirstName()
	                )
	        );

	    }



	    if(request.getLastName()!=null &&
	            !request.getLastName().isBlank()){

	        spec = spec.and(
	                FacultySpecification.hasLastName(
	                        request.getLastName()
	                )
	        );

	    }



	    if(request.getEmployeeCode()!=null &&
	            !request.getEmployeeCode().isBlank()){

	        spec = spec.and(
	                FacultySpecification.hasEmployeeCode(
	                        request.getEmployeeCode()
	                )
	        );

	    }



	    if(request.getDepartmentId()!=null){

	        spec = spec.and(
	                FacultySpecification.hasDepartmentId(
	                        request.getDepartmentId()
	                )
	        );

	    }



	    if(request.getDesignation()!=null){

	        spec = spec.and(
	                FacultySpecification.hasDesignation(
	                        request.getDesignation()
	                )
	        );

	    }



	    if(request.getQualification()!=null){

	        spec = spec.and(
	                FacultySpecification.hasQualification(
	                        request.getQualification()
	                )
	        );

	    }



	    Sort sort =
	            direction.equalsIgnoreCase("asc")
	            ?
	            Sort.by(sortBy).ascending()
	            :
	            Sort.by(sortBy).descending();



	    Pageable pageable =
	            PageRequest.of(
	                    page,
	                    size,
	                    sort
	            );



	    Page<Faculty> facultyPage =
	            facultyRepository.findAll(
	                    spec,
	                    pageable
	            );



	    PageResponse<FacultyResponseDto> response =
	            new PageResponse<>();


	    response.setContent(
	            facultyPage.getContent()
	            .stream()
	            .map(mapper::convertToResponseDto)
	            .toList()
	    );


	    response.setPageNumber(
	            facultyPage.getNumber()
	    );


	    response.setPageSize(
	            facultyPage.getSize()
	    );


	    response.setTotalElements(
	            facultyPage.getTotalElements()
	    );


	    response.setTotalPages(
	            facultyPage.getTotalPages()
	    );


	    response.setLast(
	            facultyPage.isLast()
	    );


	    return response;

	}

}