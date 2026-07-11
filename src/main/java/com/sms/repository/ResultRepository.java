package com.sms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.Result;
import com.sms.enums.RecordStatus;

public interface ResultRepository extends JpaRepository<Result, Long>, JpaSpecificationExecutor<Result> {

	// ==============================
	// Get Active Result By Id
	// ==============================

	Optional<Result> findByIdAndRecordStatus(Long id, RecordStatus recordStatus);

	
	// ==============================
	// Pagination
	// Active / Deleted Result Filter
	// ==============================

	Page<Result> findByRecordStatus(RecordStatus recordStatus, Pageable pageable);
	
	// ==============================
	// Duplicate Result Check
	// Student + Exam Combination
	// ==============================
	
	 boolean existsByStudentIdAndExamIdAndRecordStatus(
	            Long studentId,
	            Long examId,
	            RecordStatus recordStatus
	    );

}