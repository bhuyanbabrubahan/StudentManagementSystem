package com.sms.fee.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.enums.RecordStatus;
import com.sms.fee.entity.StudentFeeReceipt;



public interface StudentFeeReceiptRepository
        extends JpaRepository<StudentFeeReceipt, Long>,
                JpaSpecificationExecutor<StudentFeeReceipt> {



    // =====================================================
    // Find Receipt By Id (Soft Delete Support)
    // =====================================================


    Optional<StudentFeeReceipt> findByIdAndStatusNot(
            Long id,
            RecordStatus status
    );





    // =====================================================
    // Get All Active Receipts
    // =====================================================


    Page<StudentFeeReceipt> findByStatusNot(
            RecordStatus status,
            Pageable pageable
    );





    // =====================================================
    // Duplicate Receipt Validation
    // =====================================================


    boolean existsByPaymentIdAndStatusNot(
            Long paymentId,
            RecordStatus status
    );





    // =====================================================
    // Receipt Number Validation
    // =====================================================


    Optional<StudentFeeReceipt> findByReceiptNumberAndStatusNot(
            String receiptNumber,
            RecordStatus status
    );





    // =====================================================
    // Latest Receipt For Number Generation
    // =====================================================


    Optional<StudentFeeReceipt> findTopByOrderByIdDesc();





    // =====================================================
    // Dashboard Statistics
    // =====================================================


    long countByStatusNot(
            RecordStatus status
    );





    // =====================================================
    // Date Range Report Support
    // =====================================================


    long countByReceiptDateBetweenAndStatusNot(
            java.time.LocalDate fromDate,
            java.time.LocalDate toDate,
            RecordStatus status
    );





    // =====================================================
    // Payment Based Search
    // =====================================================


    Optional<StudentFeeReceipt> findByPaymentIdAndStatusNot(
            Long paymentId,
            RecordStatus status
    );



}