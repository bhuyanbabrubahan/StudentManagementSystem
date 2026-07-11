package com.sms.entity;


import java.math.BigDecimal;

import com.sms.enums.RecordStatus;
import com.sms.enums.ResultStatus;

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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(
        name = "results",

        uniqueConstraints = {

                @UniqueConstraint(
                        name = "uk_result_student_exam",
                        columnNames = {
                                "student_id",
                                "exam_id"
                        }
                )

        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    // ==========================
    // Student Relation
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    @ToString.Exclude
    private Student student;



    // ==========================
    // Exam Relation
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "exam_id",
            nullable = false
    )
    @ToString.Exclude
    private Exam exam;



    // ==========================
    // Marks Information
    // ==========================

    @Column(
            name = "obtained_marks",
            nullable = false
    )
    private Integer obtainedMarks;



    @Column(
            nullable = false,
            precision = 5,
            scale = 2
    )
    private BigDecimal percentage;



    @Column(
    		nullable = false, length = 10
    )
    private String grade;



    // ==========================
    // Result Information
    // ==========================

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private ResultStatus resultStatus;



    @Column(
            length = 500
    )
    private String remarks;



    // ==========================
    // Soft Delete
    // ==========================

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private RecordStatus recordStatus = RecordStatus.ACTIVE;

}