package com.sms.specification;

import org.springframework.data.jpa.domain.Specification;

import com.sms.entity.Designation;
import com.sms.entity.Faculty;
import com.sms.entity.Qualification;
import com.sms.enums.RecordStatus;

public class FacultySpecification {
	
	
	public static Specification<Faculty> hasId(Long id){

	    return (root, query, cb) ->

	            cb.equal(
	                root.get("id"),
	                id
	            );
	}

	// ============================
	// Status Filter
	// ============================

	public static Specification<Faculty> hasStatus(RecordStatus status) {

		return (root, query, cb) ->

		cb.equal(root.get("status"), status);

	}

	// ============================
	// First Name Search
	// ============================

	public static Specification<Faculty> hasFirstName(String firstName) {

		return (root, query, cb) ->

		cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");

	}

	// ============================
	// Last Name Search
	// ============================

	public static Specification<Faculty> hasLastName(String lastName) {

		return (root, query, cb) ->

		cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");

	}

	// ============================
	// Employee Code Search
	// ============================

	public static Specification<Faculty> hasEmployeeCode(String employeeCode) {

		return (root, query, cb) ->

		cb.equal(root.get("employeeCode"), employeeCode);

	}

	// ============================
	// Department Filter
	// ============================

	public static Specification<Faculty> hasDepartmentId(Long departmentId) {

		return (root, query, cb) ->

		cb.equal(root.get("department").get("id"), departmentId);

	}

	// ============================
	// Designation Filter
	// ============================

	public static Specification<Faculty> hasDesignation(Designation designation) {

		return (root, query, cb) ->

		cb.equal(root.get("designation"), designation);

	}

	// ============================
	// Qualification Filter
	// ============================

	public static Specification<Faculty> hasQualification(Qualification qualification) {

		return (root, query, cb) ->

		cb.equal(root.get("qualification"), qualification);

	}

}