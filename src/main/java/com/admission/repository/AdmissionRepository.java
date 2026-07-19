package com.admission.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.admission.entity.Admission;
import com.admission.entity.AdmissionStatus;

public interface AdmissionRepository extends JpaRepository<Admission, Long>, JpaSpecificationExecutor<Admission> {

	/*
	 * Find admission by id
	 *
	 * Cancelled admission normal fetch me nahi chahiye.
	 */
	Optional<Admission> findByIdAndStatusNot(Long id, AdmissionStatus status);

	/*
	 * Check duplicate active admission
	 *
	 * One student can have only one ACTIVE admission.
	 */
	boolean existsByStudent_IdAndStatus(Long studentId, AdmissionStatus status);

	/*
	 * Pagination with status filtering
	 *
	 * Cancelled admission exclude karne ke liye.
	 */
	Page<Admission> findByStatusNot(AdmissionStatus status, Pageable pageable);

	/*
	 * Find admission by admission number
	 *
	 * Admission number unique hona chahiye.
	 */
	Optional<Admission> findByAdmissionNumber(String admissionNumber);

	/*
	 * Check duplicate admission number
	 *
	 * Code generator safety ke liye.
	 */
	boolean existsByAdmissionNumber(String admissionNumber);
	
	
	//DASHBOARD
	long count();

	long countByStatus(AdmissionStatus status);

}