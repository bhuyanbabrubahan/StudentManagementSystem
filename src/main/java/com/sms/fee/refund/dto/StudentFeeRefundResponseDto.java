package com.sms.fee.refund.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

import com.sms.fee.refund.enums.RefundMode;
import com.sms.fee.refund.enums.RefundStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Schema(
        name = "StudentFeeRefundResponseDto",
        description = "Response DTO containing student fee refund details"
)
public class StudentFeeRefundResponseDto {



    private Long id;



    @Schema(
            example = "SMS-REF-20260721-000001"
    )
    private String refundNumber;



    // =====================================================
    // Payment Details
    // =====================================================


    private Long paymentId;


    private String receiptNumber;




    // =====================================================
    // Student Snapshot
    // =====================================================


    private Long studentId;


    private String studentName;


    private String rollNumber;




    // =====================================================
    // Admission Snapshot
    // =====================================================


    private Long admissionId;


    private String admissionNumber;




    // =====================================================
    // Refund Details
    // =====================================================


    private BigDecimal refundAmount;


    private String refundReason;


    private LocalDate refundDate;


    private RefundMode refundMode;


    private RefundStatus refundStatus;




    // =====================================================
    // Transaction
    // =====================================================


    private String transactionReference;


    private String remarks;



}