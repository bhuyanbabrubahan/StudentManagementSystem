package com.sms.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.sms.entity.Course;
import com.sms.enums.CourseStatus;

public class CourseSpecification {

    // 1. STATUS (default ACTIVE handled in service layer)
    public static Specification<Course> hasStatus(CourseStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    // 2. COURSE NAME (LIKE search)
    public static Specification<Course> hasCourseName(String courseName) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("courseName")),
                        "%" + courseName.toLowerCase() + "%"
                );
    }

    // 3. COURSE CODE (exact match)
    public static Specification<Course> hasCourseCode(String courseCode) {
        return (root, query, cb) ->
                cb.equal(root.get("courseCode"), courseCode);
    }

    // 4. DEPARTMENT FILTER (JOIN handled by Hibernate)
    public static Specification<Course> hasDepartment(Long departmentId) {
        return (root, query, cb) ->
                cb.equal(root.get("department").get("id"), departmentId);
    }

    // 5. MIN FEES
    public static Specification<Course> hasFeesGreaterThan(BigDecimal minFees) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("fees"), minFees);
    }

    // 6. MAX FEES
    public static Specification<Course> hasFeesLessThan(BigDecimal maxFees) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("fees"), maxFees);
    }

    // 7. OPTIONAL: ACTIVE ONLY helper (optional reuse)
    public static Specification<Course> isActive() {
        return (root, query, cb) ->
                cb.equal(root.get("status"), CourseStatus.ACTIVE);
    }
}