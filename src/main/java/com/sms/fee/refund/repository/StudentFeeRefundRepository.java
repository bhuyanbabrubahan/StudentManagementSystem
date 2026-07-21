package com.sms.fee.refund.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import com.sms.enums.RecordStatus;
import com.sms.fee.refund.entity.StudentFeeRefund;



public interface StudentFeeRefundRepository
        extends JpaRepository<StudentFeeRefund, Long>,
                JpaSpecificationExecutor<StudentFeeRefund> {



    // =====================================================
    // Find By Id (Soft Delete Support)
    // =====================================================


    Optional<StudentFeeRefund> findByIdAndStatusNot(
            Long id,
            RecordStatus status
    );





    // =====================================================
    // Get All Active Refunds
    // =====================================================


    Page<StudentFeeRefund> findByStatusNot(
            RecordStatus status,
            Pageable pageable
    );





    // =====================================================
    // Duplicate Refund Number Check
    // =====================================================


    boolean existsByRefundNumberAndStatusNot(
            String refundNumber,
            RecordStatus status
    );





    // =====================================================
    // Payment Refund Check
    // =====================================================
    

    boolean existsByPaymentIdAndStatusNot(
            Long paymentId,
            RecordStatus status
    );





    // =====================================================
    // Student Refund History
    // =====================================================


    Page<StudentFeeRefund> findByStudentIdAndStatusNot(
            Long studentId,
            RecordStatus status,
            Pageable pageable
    );





    // =====================================================
    // Payment Refund History
    // =====================================================


    Page<StudentFeeRefund> findByPaymentIdAndStatusNot(
            Long paymentId,
            RecordStatus status,
            Pageable pageable
    );



}