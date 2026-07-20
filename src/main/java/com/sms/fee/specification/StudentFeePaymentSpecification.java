package com.sms.fee.specification;


import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.sms.fee.entity.StudentFeePayment;
import com.sms.fee.enums.PaymentMode;
import com.sms.fee.enums.PaymentStatus;
import com.sms.enums.RecordStatus;



public class StudentFeePaymentSpecification {



    // =====================================================
    // Main Specification Builder
    // =====================================================


    public static Specification<StudentFeePayment> 
    getSpecification(
            Long studentId,
            Long admissionId,
            String academicYear,
            PaymentStatus paymentStatus,
            PaymentMode paymentMode,
            BigDecimal minPaidAmount,
            BigDecimal maxPaidAmount,
            RecordStatus status
    ){


        return Specification
                .where(hasStudentId(studentId))
                .and(hasAdmissionId(admissionId))
                .and(hasAcademicYear(academicYear))
                .and(hasPaymentStatus(paymentStatus))
                .and(hasPaymentMode(paymentMode))
                .and(hasMinPaidAmount(minPaidAmount))
                .and(hasMaxPaidAmount(maxPaidAmount))
                .and(hasStatus(status));

    }





    // =====================================================
    // Student Filter
    // =====================================================


    private static Specification<StudentFeePayment> 
    hasStudentId(Long studentId){

        return (root, query, cb) -> {

            if(studentId == null){

                return null;
            }

            return cb.equal(
                    root.get("student").get("id"),
                    studentId
            );
        };
    }





    // =====================================================
    // Admission Filter
    // =====================================================


    private static Specification<StudentFeePayment> 
    hasAdmissionId(Long admissionId){

        return (root, query, cb) -> {

            if(admissionId == null){

                return null;
            }


            return cb.equal(
                    root.get("admission").get("id"),
                    admissionId
            );
        };
    }





    // =====================================================
    // Academic Year
    // =====================================================


    private static Specification<StudentFeePayment> 
    hasAcademicYear(String academicYear){

        return (root, query, cb) -> {


            if(academicYear == null 
                    || academicYear.isBlank()){

                return null;
            }


            return cb.equal(
                    root.get("academicYear"),
                    academicYear
            );
        };
    }





    // =====================================================
    // Payment Status
    // =====================================================


    private static Specification<StudentFeePayment> 
    hasPaymentStatus(
            PaymentStatus paymentStatus
    ){

        return (root, query, cb) -> {


            if(paymentStatus == null){

                return null;
            }


            return cb.equal(
                    root.get("paymentStatus"),
                    paymentStatus
            );
        };
    }





    // =====================================================
    // Payment Mode
    // =====================================================


    private static Specification<StudentFeePayment> 
    hasPaymentMode(
            PaymentMode paymentMode
    ){

        return (root, query, cb) -> {


            if(paymentMode == null){

                return null;
            }


            return cb.equal(
                    root.get("paymentMode"),
                    paymentMode
            );
        };
    }





    // =====================================================
    // Minimum Paid Amount
    // =====================================================


    private static Specification<StudentFeePayment> 
    hasMinPaidAmount(
            BigDecimal minPaidAmount
    ){

        return (root, query, cb) -> {


            if(minPaidAmount == null){

                return null;
            }


            return cb.greaterThanOrEqualTo(
                    root.get("paidAmount"),
                    minPaidAmount
            );
        };
    }





    // =====================================================
    // Maximum Paid Amount
    // =====================================================


    private static Specification<StudentFeePayment> 
    hasMaxPaidAmount(
            BigDecimal maxPaidAmount
    ){

        return (root, query, cb) -> {


            if(maxPaidAmount == null){

                return null;
            }


            return cb.lessThanOrEqualTo(
                    root.get("paidAmount"),
                    maxPaidAmount
            );
        };
    }





    // =====================================================
    // Record Status
    // =====================================================


    private static Specification<StudentFeePayment> 
    hasStatus(
            RecordStatus status
    ){

        return (root, query, cb) -> {


            if(status == null){

                return cb.notEqual(
                        root.get("status"),
                        RecordStatus.DELETED
                );
            }


            return cb.equal(
                    root.get("status"),
                    status
            );
        };
    }


}