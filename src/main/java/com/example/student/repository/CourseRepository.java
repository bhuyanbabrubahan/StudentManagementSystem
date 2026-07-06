package com.example.student.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.student.entity.Course;
import com.example.student.entity.CourseStatus;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course>{

	Optional<Course> findByIdAndStatusNot(Long id, CourseStatus status);

	Page<Course> findByStatusNot(CourseStatus status, Pageable pageable);
}
