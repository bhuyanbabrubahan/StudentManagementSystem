package com.sms.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.sms.entity.Course;
import com.sms.enums.RecordStatus;

public class CourseSpecification {

    // ==========================================
    // Status
    // ==========================================

    public static Specification<Course> hasStatus(RecordStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    // ==========================================
    // Course Name
    // ==========================================

    public static Specification<Course> hasCourseName(String courseName) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("courseName")),
                        "%" + courseName.trim().toLowerCase() + "%"
                );
    }

    // ==========================================
    // Course Code
    // ==========================================

    public static Specification<Course> hasCourseCode(String courseCode) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("courseCode")),
                        "%" + courseCode.trim().toLowerCase() + "%"
                );
    }

    // ==========================================
    // Department
    // ==========================================

    public static Specification<Course> hasDepartment(Long departmentId) {
        return (root, query, cb) ->
                cb.equal(
                        root.get("department").get("id"),
                        departmentId
                );
    }

    // ==========================================
    // Minimum Fees
    // ==========================================

    public static Specification<Course> hasFeesGreaterThan(BigDecimal minFees) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(
                        root.get("fees"),
                        minFees
                );
    }

    // ==========================================
    // Maximum Fees
    // ==========================================

    public static Specification<Course> hasFeesLessThan(BigDecimal maxFees) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(
                        root.get("fees"),
                        maxFees
                );
    }

    // ==========================================
    // Minimum Duration
    // ==========================================

    public static Specification<Course> hasMinDuration(Integer minDuration) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(
                        root.get("durationInMonths"),
                        minDuration
                );
    }

    // ==========================================
    // Maximum Duration
    // ==========================================

    public static Specification<Course> hasMaxDuration(Integer maxDuration) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(
                        root.get("durationInMonths"),
                        maxDuration
                );
    }
}