package com.admission.mapper;

import org.springframework.stereotype.Component;

import com.admission.dto.AdmissionRequestDto;
import com.admission.dto.AdmissionResponseDto;
import com.admission.entity.Admission;
import com.sms.entity.Course;
import com.sms.entity.Department;
import com.sms.entity.Student;

@Component
public class AdmissionMapper {

	public Admission toEntity(AdmissionRequestDto dto) {

		Admission admission = new Admission();

		admission.setAcademicYear(dto.getAcademicYear());
		admission.setSemester(dto.getSemester());
		if(dto.getAdmissionDate()!=null){
	        admission.setAdmissionDate(dto.getAdmissionDate());
	    }

		admission.setRemarks(dto.getRemarks());

		return admission;
	}

	public AdmissionResponseDto toDto(Admission admission) {

		AdmissionResponseDto dto = new AdmissionResponseDto();

		dto.setId(admission.getId());

		dto.setAdmissionNumber(admission.getAdmissionNumber());

		dto.setStudentId(admission.getStudent().getId());

		dto.setStudentRollNumber(admission.getStudent().getRollNumber());

		dto.setStudentName(admission.getStudent().getFirstName() + " " + admission.getStudent().getLastName());

		dto.setDepartmentId(admission.getDepartment().getId());

		dto.setDepartmentName(admission.getDepartment().getDepartmentName());

		dto.setCourseId(admission.getCourse().getId());

		dto.setCourseName(admission.getCourse().getCourseName());

		dto.setAcademicYear(admission.getAcademicYear());

		dto.setSemester(admission.getSemester());

		dto.setAdmissionDate(admission.getAdmissionDate());

		dto.setStatus(admission.getStatus());

		dto.setRemarks(admission.getRemarks());

		dto.setCreatedAt(admission.getCreatedAt());

		dto.setUpdatedAt(admission.getUpdatedAt());

		return dto;
	}

	public void updateEntity(Admission admission, AdmissionRequestDto dto) {

		Student student = new Student();
		student.setId(dto.getStudentId());
		admission.setStudent(student);

		Department department = new Department();
		department.setId(dto.getDepartmentId());
		admission.setDepartment(department);

		Course course = new Course();
		course.setId(dto.getCourseId());
		admission.setCourse(course);

		admission.setAcademicYear(dto.getAcademicYear());
		admission.setSemester(dto.getSemester());
		if(dto.getAdmissionDate()!=null){

	        admission.setAdmissionDate(
	                dto.getAdmissionDate()
	        );
	    }

		admission.setRemarks(dto.getRemarks());
	}

}