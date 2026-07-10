package com.sms.specification;


import org.springframework.data.jpa.domain.Specification;

import com.sms.entity.Semester;
import com.sms.enums.SemesterStatus;


public class SemesterSpecification {


    public static Specification<Semester> hasStatus(
            SemesterStatus status
    ){

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.equal(
                        root.get("status"),
                        status
                );
    }





    public static Specification<Semester> hasSemesterName(
            String semesterName
    ){

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.like(
                        criteriaBuilder.lower(
                                root.get("semesterName")
                        ),
                        "%" + semesterName.toLowerCase() + "%"
                );

    }







    public static Specification<Semester> hasSemesterNumber(
            Integer semesterNumber
    ){

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.equal(
                        root.get("semesterNumber"),
                        semesterNumber
                );

    }







    public static Specification<Semester> hasCourse(
            Long courseId
    ){

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.equal(
                        root.get("course").get("id"),
                        courseId
                );

    }


}