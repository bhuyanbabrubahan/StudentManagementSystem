package com.sms.scholarship.entity;



import java.math.BigDecimal;
import java.time.LocalDate;

import com.admission.entity.Admission;
import com.sms.common.entity.BaseEntity;
import com.sms.entity.Student;
import com.sms.enums.RecordStatus;
import com.sms.scholarship.enums.ScholarshipStatus;
import com.sms.scholarship.enums.ScholarshipType;

import jakarta.persistence.Column;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "student_scholarships"
)
public class StudentScholarship extends BaseEntity {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    private Student student;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "admission_id",
            nullable = false
    )
    private Admission admission;



    @Column(
            name = "scholarship_name",
            nullable = false,
            length = 100
    )
    private String scholarshipName;



    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false
    )
    private ScholarshipType scholarshipType;



    @Column(
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal scholarshipValue;



    @Column(
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal requestedAmount;



    @Column(
            precision = 10,
            scale = 2
    )
    private BigDecimal approvedAmount;



    @Column(
            nullable = false,
            length = 20
    )
    private String academicYear;



    private LocalDate approvalDate;



    @Column(length = 500)
    private String remarks;

    @Column(
            name = "rejection_reason",
            length = 500
    )
    private String rejectionReason;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private ScholarshipStatus scholarshipStatus;
    

    @Enumerated(EnumType.STRING)
    private RecordStatus status;

}
