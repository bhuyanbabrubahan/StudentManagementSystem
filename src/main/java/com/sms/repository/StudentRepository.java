package com.sms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.Student;
import com.sms.enums.StudentStatus;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student>{

	
	Optional<Student> findByIdAndStatus(Long id, StudentStatus status);
	
	Page<Student> findByStatus(StudentStatus status, Pageable pageable);
	
	
	long countByDepartmentIdAndStatus(Long departmentId, StudentStatus status);
	
	boolean existsByDepartment_Id(Long departmentId);
	

	long countByDepartment_Id(Long departmentId);
	
	
	
	Optional<Student> findByIdAndStatusNot(Long id, StudentStatus status);
}
