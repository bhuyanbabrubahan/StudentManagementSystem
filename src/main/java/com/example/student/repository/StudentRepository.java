package com.example.student.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.student.entity.Student;
import com.example.student.entity.StudentStatus;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student>{

	boolean existsByEmail(String email);
	
	Optional<Student> findByIdAndStatus(Long id, StudentStatus status);
	
	Page<Student> findByStatus(StudentStatus status, Pageable pageable);
	
	
	
	long countByDepartmentIdAndStatus(Long departmentId, StudentStatus status);
}
