package com.example.student.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.example.student.entity.Gender;
import com.example.student.entity.Student;
import com.example.student.entity.StudentStatus;

public class StudentSpecification {

	public static Specification<Student> hasStatus(StudentStatus status) {

	    return (root, query, criteriaBuilder) ->
	            criteriaBuilder.equal(root.get("status"), status);

	}
	
	public static Specification<Student> hasGender(Gender gender){
		
		return (root, query, criticalBuilder) ->
				criticalBuilder.equal(root.get("gender"), gender);
	}
	
	public static Specification<Student> hasFeesGreaterThan(BigDecimal fees){
		
		return (root, query, criticalBuilder) ->
				criticalBuilder.greaterThan(root.get("fees"), fees);
	}
}
