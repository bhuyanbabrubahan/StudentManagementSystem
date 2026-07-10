package com.sms.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.sms.entity.Gender;
import com.sms.entity.Student;
import com.sms.enums.StudentStatus;

public class StudentSpecification { 

	public static Specification<Student> hasStatus(StudentStatus status) {

	    return (root, query, criteriaBuilder) ->
	            criteriaBuilder.equal(root.get("status"), status);

	}
	
	public static Specification<Student> hasGender(Gender gender){
		
		return (root, query, criteriaBuilder) ->
			criteriaBuilder.equal(root.get("gender"), gender);
	}
	
	public static Specification<Student> hasFeesGreaterThan(BigDecimal fees){
		
		return (root, query, criteriaBuilder) ->
			criteriaBuilder.greaterThan(root.get("fees"), fees);
	}
}
