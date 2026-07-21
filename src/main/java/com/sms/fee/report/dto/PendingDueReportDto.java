package com.sms.fee.report.dto;


import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Schema(
        name = "PendingDueReportDto",
        description = "DTO containing student wise pending fee details"
)
public class PendingDueReportDto {



    // =====================================================
    // Student Information
    // =====================================================


    @Schema(
            description = "Student unique identifier",
            example = "1"
    )
    private Long studentId;



    @Schema(
            description = "Student full name",
            example = "Rahul Kumar Sharma"
    )
    private String studentName;



    @Schema(
            description = "Student roll number",
            example = "STU20260007"
    )
    private String rollNumber;



    // =====================================================
    // Admission Information
    // =====================================================


    @Schema(
            description = "Admission unique identifier",
            example = "1"
    )
    private Long admissionId;



    @Schema(
            description = "Admission number",
            example = "ADM20260001"
    )
    private String admissionNumber;



    @Schema(
            description = "Course name",
            example = "B.Tech Information Technology"
    )
    private String courseName;



    @Schema(
            description = "Department name",
            example = "Information Technology"
    )
    private String departmentName;




    // =====================================================
    // Academic Information
    // =====================================================


    @Schema(
            description = "Academic year",
            example = "2026-2027"
    )
    private String academicYear;




    // =====================================================
    // Fee Summary
    // =====================================================


    @Schema(
            description = "Total assigned fee amount",
            example = "58000.00"
    )
    private BigDecimal totalFee;



    @Schema(
            description = "Total paid fee amount",
            example = "55000.00"
    )
    private BigDecimal paidAmount;



    @Schema(
            description = "Remaining due fee amount",
            example = "3000.00"
    )
    private BigDecimal dueAmount;



}