package com.sms.fee.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

import com.sms.enums.RecordStatus;
import com.sms.fee.enums.PaymentMode;
import com.sms.fee.enums.PaymentStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Schema(
    name = "StudentFeePaymentResponseDto",
    description = "Response DTO containing student fee payment details."
)
public class StudentFeePaymentResponseDto {



    // ==========================
    // Primary Key
    // ==========================


    @Schema(
        description = "Payment ID",
        example = "1"
    )
    private Long id;


    @Schema(
            description = "Scholarship Id",
            example = "1"
    )
    private Long scholarshipId;

    @Schema(
            description = "Scholarship Amount",
            example = "20000.00"
    )
    private BigDecimal scholarshipAmount;

    @Schema(
            description = "Final payable fee after scholarship adjustment",
            example = "50000.00"
    )
    private BigDecimal finalPayableAmount;
    
    
    // ==========================
    // Student
    // ==========================


    @Schema(
        description = "Student ID",
        example = "1"
    )
    private Long studentId;


    @Schema(
        description = "Student name",
        example = "Rahul Kumar"
    )
    private String studentName;




    // ==========================
    // Admission
    // ==========================


    @Schema(
        description = "Admission ID",
        example = "1"
    )
    private Long admissionId;



    // ==========================
    // Academic
    // ==========================


    @Schema(
        description = "Academic year",
        example = "2026-2027"
    )
    private String academicYear;



    // ==========================
    // Fee Details
    // ==========================


    @Schema(
        description = "Total fee calculated from fee structure",
        example = "58000"
    )
    private BigDecimal totalFee;



    @Schema(
        description = "Amount paid",
        example = "30000"
    )
    private BigDecimal paidAmount;



    @Schema(
        description = "Remaining due amount",
        example = "28000"
    )
    private BigDecimal dueAmount;




    // ==========================
    // Payment Status
    // ==========================


    @Schema(
        description = "Payment status",
        example = "PARTIAL"
    )
    private PaymentStatus paymentStatus;




    @Schema(
        description = "Payment date",
        example = "2026-07-20"
    )
    private LocalDate paymentDate;



    @Schema(
        description = "Payment mode",
        example = "UPI"
    )
    private PaymentMode paymentMode;




    @Schema(
        description = "Transaction reference",
        example = "UPI987654"
    )
    private String transactionReference;




    @Schema(
        description = "Remarks"
    )
    private String remarks;




    // ==========================
    // Audit
    // ==========================


    @Schema(
            description = "Payment record status",
            example = "ACTIVE"
    )
    private RecordStatus status;

}