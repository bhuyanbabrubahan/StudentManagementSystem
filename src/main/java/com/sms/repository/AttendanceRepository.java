package com.sms.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.Attendance;
import com.sms.enums.AttendanceRecordStatus;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

	// ==========================
	// Duplicate Check CREATE
	// ==========================

	boolean existsByStudentIdAndFacultySubjectMappingIdAndAttendanceDateAndStatusNot(Long studentId,
			Long facultySubjectMappingId, LocalDate attendanceDate, AttendanceRecordStatus status);

	/*
	 * select count(*) from attendance where student_id=? and
	 * faculty_subject_mapping_id=? and attendance_date=? and status!='DELETED';
	 */

	// ==========================
	// Duplicate Check UPDATE
	// ==========================

	boolean existsByStudentIdAndFacultySubjectMappingIdAndAttendanceDateAndStatusNotAndIdNot(Long studentId,
			Long facultySubjectMappingId, LocalDate attendanceDate, AttendanceRecordStatus status, Long id);

	// ==========================
	// Find Active Attendance
	// ==========================

	Optional<Attendance> findByIdAndStatusNot(Long id, AttendanceRecordStatus status);

	// ==========================
	// Pagination
	// ==========================

	Page<Attendance> findByStatusNot(AttendanceRecordStatus status, Pageable pageable);

}