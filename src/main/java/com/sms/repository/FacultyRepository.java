package com.sms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.Faculty;
import com.sms.entity.FacultyStatus;

public interface FacultyRepository extends JpaRepository<Faculty, Long>, JpaSpecificationExecutor<Faculty> {

	Optional<Faculty> findByIdAndStatus(Long id, FacultyStatus status);

	boolean existsByEmployeeCode(String employeeCode);
	
	Optional<Faculty> findByUserId(Long userId);

	Page<Faculty> findByStatus(FacultyStatus status, Pageable pageable);
	

}