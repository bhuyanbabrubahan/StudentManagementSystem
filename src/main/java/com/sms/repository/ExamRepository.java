package com.sms.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.Exam;
import com.sms.entity.Student;
import com.sms.enums.ExamType;
import com.sms.enums.RecordStatus;

public interface ExamRepository extends JpaRepository<Exam, Long>, JpaSpecificationExecutor<Exam> {

	// ==========================
	// Duplicate Check CREATE
	// ==========================

	boolean existsBySubjectIdAndExamDateAndExamTypeAndStatusNot(Long subjectId, LocalDate examDate, ExamType examType,
			RecordStatus status);

	// ==========================
	// Duplicate Check UPDATE
	// ==========================

	boolean existsBySubjectIdAndExamDateAndExamTypeAndStatusNotAndIdNot(Long subjectId, LocalDate examDate,
			ExamType examType, RecordStatus status, Long id);

	// ==========================
	// Find Active Exam
	// ==========================

	@EntityGraph(attributePaths = {
	        "semester",
	        "subject"
	})
	Optional<Exam> findByIdAndStatusNot(Long id, RecordStatus status);

	// ==========================
	// ==========================

	@EntityGraph(attributePaths = {
	        "semester",
	        "subject"
	})
	Page<Exam> findByStatusNot(RecordStatus status, Pageable pageable);

	Optional<Student> findByIdAndStatus(Long examId, RecordStatus active);
	
	//DASHBOARD
	long countByStatusNot(RecordStatus status);

	long countByExamDateAfterAndStatusNot(
	        LocalDate examDate,
	        RecordStatus status
	);

	long countByExamDateBeforeAndStatusNot(
	        LocalDate examDate,
	        RecordStatus status
	);

}