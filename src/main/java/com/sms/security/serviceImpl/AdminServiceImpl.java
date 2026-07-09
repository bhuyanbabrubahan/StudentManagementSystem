package com.sms.security.serviceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sms.exception.ResourceAlreadyExistsException;
import com.sms.security.dto.AdminCreateRequestDto;
import com.sms.security.dto.AdminResponseDto;
import com.sms.security.entity.Role;
import com.sms.security.entity.User;
import com.sms.security.entity.UserStatus;
import com.sms.security.repository.UserRepository;
import com.sms.security.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public AdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;

		this.passwordEncoder = passwordEncoder;

	}

	@Override
	public AdminResponseDto createAdmin(AdminCreateRequestDto request) {

		if (userRepository.existsByEmail(request.getEmail())) {

			throw new ResourceAlreadyExistsException("Email already exists");

		}

		User admin = new User();

		admin.setEmail(request.getEmail());

		admin.setPassword(passwordEncoder.encode(request.getPassword()));

		admin.setRole(Role.ADMIN);

		admin.setStatus(UserStatus.ACTIVE);

		admin = userRepository.save(admin);

		return new AdminResponseDto(

				admin.getId(),

				admin.getEmail(),

				admin.getRole().name(),

				"Admin created successfully"

		);

	}

}