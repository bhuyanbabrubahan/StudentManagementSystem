package com.sms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.Student;
import com.sms.enums.RecordStatus;
import com.sms.security.entity.User;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

	Optional<Student> findByIdAndStatus(Long id, RecordStatus status);
	
	Optional<Student> findByIdAndStatusNot(Long id, RecordStatus status);

	Page<Student> findByStatus(RecordStatus status, Pageable pageable);

	long countByDepartmentIdAndStatus(Long departmentId, RecordStatus status);

	boolean existsByDepartment_Id(Long departmentId);

	long countByDepartment_Id(Long departmentId);

	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

	Optional<Student> findByRollNumberAndStatus(String rollNumber, RecordStatus status);

	boolean existsByUserId(Long userId);

	boolean existsByAddressId(Long addressId);
	
	
	Optional<Student> findByUser(User user);
	
	Optional<Student> findByUserIdAndStatus(
	        Long userId,
	        RecordStatus status
	);
	
}
