package com.sms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.FacultySubjectMapping;
import com.sms.enums.FacultySubjectStatus;

public interface FacultySubjectRepository
		extends JpaRepository<FacultySubjectMapping, Long>, JpaSpecificationExecutor<FacultySubjectMapping> {

	boolean existsByFacultyIdAndSubjectIdAndAcademicYearAndStatusNot(
	        Long facultyId,
	        Long subjectId,
	        String academicYear,
	        FacultySubjectStatus status
	);

	boolean existsByFacultyIdAndSubjectIdAndAcademicYearAndStatusNotAndIdNot(
	        Long facultyId,
	        Long subjectId,
	        String academicYear,
	        FacultySubjectStatus status,
	        Long id
	);

	Optional<FacultySubjectMapping> findByIdAndStatusNot(
	        Long id,
	        FacultySubjectStatus status
	);

	Page<FacultySubjectMapping> findByStatusNot(
	        FacultySubjectStatus status,
	        Pageable pageable
	);

}