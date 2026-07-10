package com.sms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sms.entity.Semester;
import com.sms.enums.SemesterStatus;

public interface SemesterRepository extends JpaRepository<Semester, Long>, JpaSpecificationExecutor<Semester> {

	boolean existsBySemesterName(String semesterName);

	boolean existsBySemesterNameAndIdNot(String semesterName, Long id);

	Optional<Semester> findByIdAndStatusNot(Long id, SemesterStatus status);

	Page<Semester> findByStatusNot(SemesterStatus status, Pageable pageable);

}