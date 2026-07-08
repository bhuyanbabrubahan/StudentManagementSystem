package com.admission.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.admission.entity.Admission;
import com.admission.entity.AdmissionStatus;

public interface AdmissionRepository extends JpaRepository<Admission, Long>, JpaSpecificationExecutor<Admission> {

	// Used in Get By Id / Update / Delete
	Optional<Admission> findByIdAndStatusNot(Long id, AdmissionStatus status);

	// Business Validation
	boolean existsByStudent_IdAndStatus(Long studentId, AdmissionStatus status);
	
	Page<Admission> findByStatusNot(AdmissionStatus status, Pageable pageable);

}