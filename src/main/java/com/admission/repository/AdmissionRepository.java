package com.admission.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.admission.entity.Admission;
import com.admission.entity.AdmissionStatus;

public interface AdmissionRepository extends JpaRepository<Admission, Long>, JpaSpecificationExecutor<Admission> {

	// Find admission by id excluding cancelled admission
	Optional<Admission> findByIdAndAdmissionStatusNot(Long id, AdmissionStatus admissionStatus);

	// Find admission by id and specific status
	Optional<Admission> findByIdAndAdmissionStatus(Long id, AdmissionStatus admissionStatus);

	// Check duplicate active admission for student
	// One student can have only one ACTIVE admission
	boolean existsByStudent_IdAndAdmissionStatus(Long studentId, AdmissionStatus admissionStatus);

	// Pagination with status filtering excluding cancelled admission
	Page<Admission> findByAdmissionStatusNot(AdmissionStatus admissionStatus, Pageable pageable);

	// Find admission by admission number
	Optional<Admission> findByAdmissionNumber(String admissionNumber);

	// Check duplicate admission number for generator safety
	boolean existsByAdmissionNumber(String admissionNumber);

	// Dashboard count of all admissions
	long count();

	// Dashboard count by admission status
	long countByAdmissionStatus(AdmissionStatus admissionStatus);

}