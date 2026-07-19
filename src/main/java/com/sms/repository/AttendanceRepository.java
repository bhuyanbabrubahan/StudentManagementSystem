package com.sms.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.Attendance;
import com.sms.enums.AttendanceStatus;
import com.sms.enums.RecordStatus;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

	
	
	// ==========================
		// Duplicate Check CREATE
		// ==========================

		boolean existsByStudentIdAndFacultySubjectMappingIdAndAttendanceDateAndStatusNot(
		        Long studentId,
		        Long facultySubjectMappingId,
		        LocalDate attendanceDate,
		        RecordStatus status
		);

		/*
		 * select count(*) from attendance where student_id=? and
		 * faculty_subject_mapping_id=? and attendance_date=? and status!='DELETED';
		 */

		// ==========================
		// Duplicate Check UPDATE
		// ==========================

		boolean existsByStudentIdAndFacultySubjectMappingIdAndAttendanceDateAndStatusNotAndIdNot(
		        Long studentId,
		        Long facultySubjectMappingId,
		        LocalDate attendanceDate,
		        RecordStatus status,
		        Long id
		);

		// ==========================
		// Find Active Attendance
		// ==========================

		Optional<Attendance> findByIdAndStatusNot(Long id, RecordStatus status);

		// ==========================
		// Pagination
		// ==========================

		Page<Attendance> findByStatusNot(RecordStatus status, Pageable pageable);
		
		
		//DASHBOARD
		long countByStatusNot(RecordStatus status);

		long countByAttendanceDateAndStatusNot(
		        LocalDate attendanceDate,
		        RecordStatus status
		);

		long countByAttendanceStatusAndAttendanceDateAndStatusNot(
		        AttendanceStatus attendanceStatus,
		        LocalDate attendanceDate,
		        RecordStatus status
		);

}