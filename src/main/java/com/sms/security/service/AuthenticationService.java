package com.sms.security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.location.address.entity.Address;
import com.location.address.repository.AddressRepository;
import com.location.village.entity.Village;
import com.location.village.repository.VillageRepository;
import com.sms.entity.Department;
import com.sms.entity.Student;
import com.sms.enums.RecordStatus;
import com.sms.exception.ResourceAlreadyExistsException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.mapper.StudentMapper;
import com.sms.repository.DepartmentRepository;
import com.sms.repository.StudentRepository;
import com.sms.security.dto.LoginRequestDto;
import com.sms.security.dto.LoginResponseDto;
import com.sms.security.dto.RegisterRequestDto;
import com.sms.security.dto.RegisterResponseDto;
import com.sms.security.entity.Role;
import com.sms.security.entity.User;
import com.sms.security.entity.UserStatus;
import com.sms.security.repository.UserRepository;
import com.sms.sequence.enums.ModuleType;
import com.sms.sequence.service.CodeGeneratorService;

@Service
public class AuthenticationService {

	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final DepartmentRepository departmentRepository;
	private final VillageRepository villageRepository;
	private final AddressRepository addressRepository;
	private final PasswordEncoder passwordEncoder;
	private final StudentMapper studentMapper;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final CodeGeneratorService codeGeneratorService;

	public AuthenticationService(

			UserRepository userRepository,
			StudentRepository studentRepository,
			DepartmentRepository departmentRepository,
			VillageRepository villageRepository,
			AddressRepository addressRepository,
			PasswordEncoder passwordEncoder,
			StudentMapper studentMapper,
			AuthenticationManager authenticationManager,
			JwtService jwtService,
			CodeGeneratorService codeGeneratorService

	) {

		this.userRepository = userRepository;
		this.studentRepository = studentRepository;
		this.departmentRepository = departmentRepository;
		this.villageRepository = villageRepository;
		this.addressRepository = addressRepository;
		this.passwordEncoder = passwordEncoder;
		this.studentMapper = studentMapper;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.codeGeneratorService = codeGeneratorService;

	}

	@Transactional
	public RegisterResponseDto register(RegisterRequestDto request) {

		// 1. Duplicate Email Check

		if (userRepository.existsByEmail(request.getEmail())) {

			throw new ResourceAlreadyExistsException("Email already exists : " + request.getEmail());

		}

		// 2. Department Validation

		Department department =

				departmentRepository.findByIdAndStatus(request.getDepartmentId(), RecordStatus.ACTIVE)
						.orElseThrow(() -> new ResourceNotFoundException("Department not found or inactive"));

		// 3. Create User

		User user = new User();

		user.setEmail(request.getEmail());

		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user.setRole(Role.STUDENT);

		user.setStatus(UserStatus.ACTIVE);

		user = userRepository.save(user);

		// 4. Create Address

		Village village =

				villageRepository.findById(request.getAddress().getVillageId())

						.orElseThrow(() -> new ResourceNotFoundException("Village not found"));

		Address address = new Address();

		address.setHouseNumber(request.getAddress().getHouseNumber());

		address.setStreet(request.getAddress().getStreet());

		address.setLandmark(request.getAddress().getLandmark());

		address.setAddressType(request.getAddress().getAddressType());

		address.setVillage(village);

		address = addressRepository.save(address);

		// 5. Create Student

		Student student =

				studentMapper.convertToEntity(request);

		student.setDepartment(department);

		student.setUser(user);

		student.setAddress(address);

		student.setRollNumber(
			    codeGeneratorService.generateCode(
			        ModuleType.STUDENT,
			        "STU"
			    )
			);

			student.setStatus(RecordStatus.ACTIVE);

		student = studentRepository.save(student);

		// 6. Response

		return new RegisterResponseDto(

				student.getId(),

				student.getRollNumber(),

				user.getEmail(),

				"Student Registration Successful"

		);

	}
	
	
	public LoginResponseDto login(
	        LoginRequestDto request
	){


	    authenticationManager.authenticate(

	            new UsernamePasswordAuthenticationToken(

	                    request.getEmail(),

	                    request.getPassword()

	            )

	    );



	    User user =

	            userRepository
	            .findByEmail(
	                    request.getEmail()
	            )
	            .orElseThrow(() ->

	                    new ResourceNotFoundException(
	                            "User not found"
	                    )

	            );


	    if(user.getStatus() != UserStatus.ACTIVE) {

	        throw new ResourceNotFoundException(
	                "User account is inactive"
	        );

	    }

	    String token =

	            jwtService.generateToken(
	                    user
	            );




	    return new LoginResponseDto(

	            token,

	            "Bearer",

	            user.getEmail(),

	            user.getRole().name()

	    );

	}
	

}