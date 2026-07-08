package com.sms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.Department;
import com.sms.entity.DepartmentStatus;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {

	boolean existsByDepartmentCode(String departmentCode);

	Optional<Department> findByIdAndStatus(Long id, DepartmentStatus status);

	Page<Department> findByStatus(DepartmentStatus status, Pageable pageable);

	boolean existsByDepartmentName(String departmentName);

	boolean existsByDepartmentNameAndIdNot(String departmentName, Long id);

	boolean existsByDepartmentCodeAndIdNot(String departmentCode, Long id);
	
	boolean existsById(Long departmentId); 

}