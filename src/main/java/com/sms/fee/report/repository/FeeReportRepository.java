package com.sms.fee.report.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sms.enums.RecordStatus;
import com.sms.fee.entity.StudentFeePayment;



@Repository
public interface FeeReportRepository  extends JpaRepository<StudentFeePayment, Long>{


    // =====================================================
    // Department Wise Fee Collection
    // =====================================================


    @Query("""
            SELECT
            d.id,
            d.departmentName,
            COUNT(DISTINCT p.student.id),
            SUM(p.totalFee),
            SUM(p.paidAmount),
            SUM(p.dueAmount)

            FROM StudentFeePayment p

            JOIN p.admission a

            JOIN a.department d

            WHERE p.status <> :status

            GROUP BY
            d.id,
            d.departmentName

            """)
    List<Object[]> getDepartmentWiseCollection(
            @Param("status")
            RecordStatus status
    );



    // =====================================================
    // Course Wise Fee Collection
    // =====================================================


    @Query("""
            SELECT
            c.id,
            c.courseName,
            d.departmentName,
            COUNT(DISTINCT p.student.id),
            SUM(p.totalFee),
            SUM(p.paidAmount),
            SUM(p.dueAmount)

            FROM StudentFeePayment p

            JOIN p.admission a

            JOIN a.course c

            JOIN a.department d

            WHERE p.status <> :status

            GROUP BY
            c.id,
            c.courseName,
            d.departmentName

            """)
    List<Object[]> getCourseWiseCollection(
            @Param("status")
            RecordStatus status
    );



    // =====================================================
    // Academic Year Wise Collection
    // =====================================================


    @Query("""
            SELECT

            p.academicYear,

            COUNT(DISTINCT p.student.id),

            SUM(p.totalFee),

            SUM(p.paidAmount),

            SUM(p.dueAmount),

            COUNT(p.id)


            FROM StudentFeePayment p


            WHERE p.status <> :status


            GROUP BY

            p.academicYear

            """)
    List<Object[]> getAcademicYearWiseCollection(
            @Param("status")
            RecordStatus status
    );


    
	 // =====================================================
	 // Payment Mode Wise Collection
	 // =====================================================
	
	
	 @Query("""
	         SELECT
	
	         p.paymentMode,
	
	         COUNT(p.id),
	
	         SUM(p.paidAmount)
	
	
	         FROM StudentFeePayment p
	
	
	         WHERE p.status <> :status
	
	
	         GROUP BY
	
	         p.paymentMode
	
	         """)
	 List<Object[]> getPaymentModeWiseCollection(
	         @Param("status")
	         RecordStatus status
	 );
	
	
	
	
	 // =====================================================
	 // Monthly Collection Report
	 // =====================================================
	
	
	 @Query("""
	         SELECT
	
	         MONTH(p.paymentDate),
	
	         YEAR(p.paymentDate),
	
	         COUNT(p.id),
	
	         SUM(p.paidAmount)
	
	
	         FROM StudentFeePayment p
	
	
	         WHERE p.status <> :status
	
	
	         GROUP BY
	
	         YEAR(p.paymentDate),
	
	         MONTH(p.paymentDate)
	
	
	         ORDER BY
	
	         YEAR(p.paymentDate),
	
	         MONTH(p.paymentDate)
	
	         """)
	 List<Object[]> getMonthlyCollection(
	         @Param("status")
	         RecordStatus status
	 );
	
	
	
	
	 // =====================================================
	 // Pending Due Student Report
	 // =====================================================
	
	
	 @Query("""
	         SELECT
	
	
	         p.student.id,
	
	         CONCAT(
	         p.student.firstName,
	         ' ',
	         p.student.lastName
	         ),
	
	
	         p.student.rollNumber,
	
	
	         a.id,
	
	
	         a.admissionNumber,
	
	
	         c.courseName,
	
	
	         d.departmentName,
	
	
	         p.academicYear,
	
	
	         p.totalFee,
	
	
	         p.paidAmount,
	
	
	         p.dueAmount
	
	
	
	         FROM StudentFeePayment p
	
	
	
	         JOIN p.admission a
	
	
	         JOIN a.course c
	
	
	         JOIN a.department d
	
	
	
	         WHERE p.dueAmount > 0
	
	         AND p.status <> :status
	
	
	         ORDER BY
	
	         p.dueAmount DESC
	
	         """)
	 List<Object[]> getPendingDueReport(
	         @Param("status")
	         RecordStatus status
	 );
    
    

}