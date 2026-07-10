package com.sms.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.sms.entity.Attendance;
import com.sms.enums.AttendanceRecordStatus;
import com.sms.enums.AttendanceStatus;


public class AttendanceSpecification {


    // ==========================
    // Student Filter
    // ==========================

    public static Specification<Attendance> hasStudent(
            Long studentId) {


        return (root, query, cb) ->

                cb.equal(
                        root.get("student").get("id"),
                        studentId
                );

    }



    // ==========================
    // Faculty Subject Filter
    // ==========================

    public static Specification<Attendance> hasFacultySubject(
            Long mappingId) {


        return (root, query, cb) ->

                cb.equal(
                        root.get("facultySubjectMapping").get("id"),
                        mappingId
                );

    }

    
    public static Specification<Attendance> hasFaculty(
            Long facultyId
    ){

        return (root, query, cb) ->

            cb.equal(
                root
                .get("facultySubjectMapping")
                .get("faculty")
                .get("id"),
                facultyId
            );
    }
    
    
    public static Specification<Attendance> hasSubject(
            Long subjectId
    ){

        return (root, query, cb) ->

            cb.equal(
                root
                .get("facultySubjectMapping")
                .get("subject")
                .get("id"),
                subjectId
            );
    }


    // ==========================
    // Attendance Date
    // ==========================

    public static Specification<Attendance> hasDate(
            LocalDate date) {


        return (root, query, cb) ->

                cb.equal(
                        root.get("attendanceDate"),
                        date
                );

    }



    // ==========================
    // Attendance Status
    // ==========================

    public static Specification<Attendance> hasAttendanceStatus(
            AttendanceStatus status) {


        return (root, query, cb) ->

                cb.equal(
                        root.get("attendanceStatus"),
                        status
                );

    }



    // ==========================
    // Record Status
    // ==========================

    public static Specification<Attendance> hasStatus(
            AttendanceRecordStatus status) {


        return (root, query, cb) ->

                cb.equal(
                        root.get("status"),
                        status
                );

    }



    // ==========================
    // Exclude Deleted
    // ==========================

    public static Specification<Attendance> statusNot(
            AttendanceRecordStatus status) {


        return (root, query, cb) ->

                cb.notEqual(
                        root.get("status"),
                        status
                );

    }

}