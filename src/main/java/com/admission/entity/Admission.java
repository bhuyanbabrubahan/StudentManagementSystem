package com.admission.entity;

import java.time.LocalDate;

import com.sms.common.entity.BaseEntity;
import com.sms.entity.Course;
import com.sms.entity.Department;
import com.sms.entity.Student;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admissions", indexes = {

		@Index(name = "idx_student", columnList = "student_id"),

		@Index(name = "idx_department", columnList = "department_id"),

		@Index(name = "idx_course", columnList = "course_id"),

		@Index(name = "idx_status", columnList = "status")

})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admission extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "admission_number", nullable = false, unique = true, length = 20)
	private String admissionNumber;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@Column(
		    name = "academic_year",
		    nullable = false,
		    length = 20
		)
		private String academicYear;

	@Column(nullable = false)
	private Integer semester;

	@Column(name = "admission_date", nullable = false)
	private LocalDate admissionDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AdmissionStatus status;

	@Column(length = 500)
	private String remarks;

}