package com.example.student.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.student.entity.Department;
import com.example.student.entity.DepartmentStatus;



public class DepartmentSpecification {

    // 1. Status filter
    public static Specification<Department> hasStatus(DepartmentStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    // 2. Department Name (LIKE search - better for real apps)
    public static Specification<Department> hasDepartmentName(String departmentName) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("departmentName")),
                        "%" + departmentName.toLowerCase() + "%"
                );
    }

    // 3. Department Code (exact match usually)
    public static Specification<Department> hasDepartmentCode(String departmentCode) {
        return (root, query, cb) ->
                cb.equal(root.get("departmentCode"), departmentCode);
    }

    // 4. Description (LIKE search instead of exact match)
    public static Specification<Department> hasDescription(String description) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("description")),
                        "%" + description.toLowerCase() + "%"
                );
    }
}
