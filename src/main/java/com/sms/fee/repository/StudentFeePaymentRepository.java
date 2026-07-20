package com.sms.fee.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.enums.RecordStatus;
import com.sms.fee.entity.StudentFeePayment;

public interface StudentFeePaymentRepository
		extends JpaRepository<StudentFeePayment, Long>, JpaSpecificationExecutor<StudentFeePayment> {

	// =====================================================
	// Find By Id (Soft Delete Support)
	// =====================================================

	Optional<StudentFeePayment> findByIdAndStatusNot(Long id, RecordStatus status);

	// =====================================================
	// Get All Active Records
	// =====================================================

	Page<StudentFeePayment> findByStatusNot(RecordStatus status, Pageable pageable);

	// =====================================================
	// Duplicate Payment Check
	// =====================================================

	boolean existsByStudentIdAndAdmissionIdAndAcademicYearAndStatusNot(Long studentId, Long admissionId,
			String academicYear, RecordStatus status);
	
	
	boolean existsByStudentIdAndAdmissionIdAndAcademicYearAndStatusNotAndIdNot(
	        Long studentId,
	        Long admissionId,
	        String academicYear,
	        RecordStatus status,
	        Long id
	);

	// =====================================================
	// Dashboard Statistics
	// =====================================================

	long countByStatusNot(RecordStatus status);

	long countByPaymentStatusAndStatusNot(String paymentStatus, RecordStatus status);

}