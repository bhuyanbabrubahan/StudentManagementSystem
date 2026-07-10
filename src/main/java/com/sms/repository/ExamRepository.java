package com.sms.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.Exam;
import com.sms.enums.ExamStatus;
import com.sms.enums.ExamType;

public interface ExamRepository extends JpaRepository<Exam, Long>, JpaSpecificationExecutor<Exam> {

	// ==========================
	// Duplicate Check CREATE
	// ==========================

	boolean existsBySubjectIdAndExamDateAndExamTypeAndStatusNot(Long subjectId, LocalDate examDate, ExamType examType,
			ExamStatus status);

	// ==========================
	// Duplicate Check UPDATE
	// ==========================

	boolean existsBySubjectIdAndExamDateAndExamTypeAndStatusNotAndIdNot(Long subjectId, LocalDate examDate,
			ExamType examType, ExamStatus status, Long id);

	// ==========================
	// Find Active Exam
	// ==========================

	Optional<Exam> findByIdAndStatusNot(Long id, ExamStatus status);

	// ==========================
	// Pagination
	// ==========================

	Page<Exam> findByStatusNot(ExamStatus status, Pageable pageable);

}