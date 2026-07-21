package com.sms.fee.refund.entity;


import java.math.BigDecimal;
import java.time.LocalDate;

import com.sms.common.entity.BaseEntity;
import com.sms.enums.RecordStatus;
import com.sms.fee.entity.StudentFeePayment;
import com.sms.fee.entity.StudentFeeReceipt;
import com.sms.fee.refund.enums.RefundMode;
import com.sms.fee.refund.enums.RefundStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;



@Entity
@Table(
        name = "student_fee_refunds",

        indexes = {


                @Index(
                        name = "idx_refund_payment",
                        columnList = "payment_id"
                ),


                @Index(
                        name = "idx_refund_receipt",
                        columnList = "receipt_id"
                ),


                @Index(
                        name = "idx_refund_student",
                        columnList = "student_id"
                ),


                @Index(
                        name = "idx_refund_admission",
                        columnList = "admission_id"
                ),


                @Index(
                        name = "idx_refund_status",
                        columnList = "refund_status"
                ),


                @Index(
                        name = "idx_refund_date",
                        columnList = "refund_date"
                )

        },


        uniqueConstraints = {


                @UniqueConstraint(
                        name = "uk_refund_number",
                        columnNames = "refund_number"
                )

        }
)


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

public class StudentFeeRefund extends BaseEntity {



    // =====================================================
    // Primary Key
    // =====================================================


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;





    // =====================================================
    // Refund Information
    // =====================================================


    @Column(
            name = "refund_number",
            nullable = false,
            unique = true,
            length = 50
    )
    private String refundNumber;



    @Column(
            name = "refund_amount",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal refundAmount;



    @Column(
            name = "refund_reason",
            length = 500
    )
    private String refundReason;



    @Column(
            name = "refund_date"
    )
    private LocalDate refundDate;





    // =====================================================
    // Payment Relationship
    // =====================================================


    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "payment_id",
            nullable = false
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private StudentFeePayment payment;





    // =====================================================
    // Receipt Relationship
    // =====================================================


    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "receipt_id"
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private StudentFeeReceipt receipt;





    // =====================================================
    // Student Snapshot
    // =====================================================


    @Column(
            name = "student_id",
            nullable = false
    )
    private Long studentId;



    @Column(
            name = "student_name",
            length = 150
    )
    private String studentName;



    @Column(
            name = "roll_number",
            length = 50
    )
    private String rollNumber;





    // =====================================================
    // Admission Snapshot
    // =====================================================


    @Column(
            name = "admission_id"
    )
    private Long admissionId;



    @Column(
            name = "admission_number",
            length = 50
    )
    private String admissionNumber;





    // =====================================================
    // Refund Mode
    // =====================================================


    @Enumerated(EnumType.STRING)
    @Column(
            name = "refund_mode",
            length = 20
    )
    private RefundMode refundMode;





    // =====================================================
    // Transaction Details
    // =====================================================


    @Column(
            name = "transaction_reference",
            length = 100
    )
    private String transactionReference;



    @Column(
            length = 500
    )
    private String remarks;





    // =====================================================
    // Refund Status
    // =====================================================


    @Enumerated(EnumType.STRING)
    @Column(
            name = "refund_status",
            nullable = false,
            length = 20
    )
    private RefundStatus refundStatus;





    // =====================================================
    // Record Status
    // =====================================================


    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private RecordStatus status;



}