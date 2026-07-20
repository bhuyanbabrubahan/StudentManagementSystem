package com.sms.fee.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.admission.entity.Admission;
import com.sms.common.entity.BaseEntity;
import com.sms.entity.Student;
import com.sms.enums.RecordStatus;
import com.sms.fee.enums.PaymentMode;
import com.sms.fee.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "student_fee_payments"
)
@Getter
@Setter
public class StudentFeePayment extends BaseEntity {


    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name="student_id",
        nullable=false
    )
    private Student student;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name="admission_id",
        nullable=false
    )
    private Admission admission;



    private String academicYear;


    private BigDecimal totalFee;


    private BigDecimal paidAmount;


    private BigDecimal dueAmount;



    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;



    private LocalDate paymentDate;



    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;



    private String transactionReference;


    private String remarks;



    @Enumerated(EnumType.STRING)
    private RecordStatus status;

}
