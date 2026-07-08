package com.admission.specification;

import org.springframework.data.jpa.domain.Specification;

import com.admission.entity.Admission;
import com.admission.entity.AdmissionStatus;
import com.sms.entity.Course;
import com.sms.entity.Department;
import com.sms.entity.Student;

import jakarta.persistence.criteria.Join;

public class AdmissionSpecification {

    //Admission Number ke basis par search karna.
    public static Specification<Admission>
    hasAdmissionNumber(String admissionNumber){

        return (root,query,cb)->

                cb.like(
                        root.get("admissionNumber"),
                        "%" + admissionNumber + "%"
                );

    }


    //Academic Year ke basis par search karna.
    public static Specification<Admission>
    hasAcademicYear(String academicYear){

        return (root,query,cb)->

                cb.equal(
                        root.get("academicYear"),
                        academicYear
                );

    }


    //Semester ke basis par search karna.
    public static Specification<Admission>
    hasSemester(Integer semester){

        return (root,query,cb)->

                cb.equal(
                        root.get("semester"),
                        semester
                );

    }


    //Admission Status ke basis par search karna.
    public static Specification<Admission>
    hasStatus(AdmissionStatus status){

        return (root,query,cb)->

                cb.equal(
                        root.get("status"),
                        status
                );

    }


    //Student ke First Name ke basis par search karna.
    public static Specification<Admission>
    hasStudentName(String name){

        return (root,query,cb)->{

            //Admission aur Student table ko join karna.
            Join<Admission,Student> student =
                    root.join("student");

            //Student ke first name par case-insensitive search karna.
            return cb.like(
                    cb.lower(student.get("firstName")),
                    "%" + name.toLowerCase() + "%"
            );

        };

    }


    //Department Id ke basis par search karna.
    public static Specification<Admission>
    hasDepartment(Long departmentId){

        return (root,query,cb)->{

            //Admission aur Department table ko join karna.
            Join<Admission,Department> department =
                    root.join("department");

            //Department Id match karna.
            return cb.equal(
                    department.get("id"),
                    departmentId
            );

        };

    }


    //Course Id ke basis par search karna.
    public static Specification<Admission>
    hasCourse(Long courseId){

        return (root,query,cb)->{

            //Admission aur Course table ko join karna.
            Join<Admission,Course> course =
                    root.join("course");

            //Course Id match karna.
            return cb.equal(
                    course.get("id"),
                    courseId
            );

        };

    }

}
