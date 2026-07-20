package com.admission.mapper;

import org.springframework.stereotype.Component;

import com.admission.dto.AdmissionRequestDto;
import com.admission.dto.AdmissionResponseDto;
import com.admission.entity.Admission;

@Component
public class AdmissionMapper {

    // ==========================================================
    // Request DTO -> Entity
    // ==========================================================

    public Admission toEntity(AdmissionRequestDto dto) {

        Admission admission = new Admission();

        admission.setAcademicYear(dto.getAcademicYear().trim());

        admission.setAdmissionDate(dto.getAdmissionDate());

        admission.setRemarks(dto.getRemarks());

        return admission;
    }

    // ==========================================================
    // Entity -> Response DTO
    // ==========================================================

    public AdmissionResponseDto toDto(Admission admission) {

        AdmissionResponseDto dto = new AdmissionResponseDto();

        // ==========================================================
        // Basic Details
        // ==========================================================

        dto.setId(admission.getId());

        dto.setAdmissionNumber(admission.getAdmissionNumber());

        dto.setAcademicYear(admission.getAcademicYear());

        if (admission.getSemester() != null) {

            dto.setSemesterId(
                    admission.getSemester().getId()
            );

            dto.setSemesterName(
                    admission.getSemester().getSemesterName()
            );

        }

        dto.setAdmissionDate(admission.getAdmissionDate());

        dto.setAdmissionStatus(admission.getAdmissionStatus());

        dto.setRemarks(admission.getRemarks());

        dto.setCreatedAt(admission.getCreatedAt());

        dto.setUpdatedAt(admission.getUpdatedAt());

        // ==========================================================
        // Student Details
        // ==========================================================

        if (admission.getStudent() != null) {

            dto.setStudentId(
                    admission.getStudent().getId()
            );

            dto.setStudentRollNumber(
                    admission.getStudent().getRollNumber()
            );

            dto.setStudentName(
                    admission.getStudent().getFullName()
            );
        }

        // ==========================================================
        // Department Details
        // ==========================================================

        if (admission.getDepartment() != null) {

            dto.setDepartmentId(
                    admission.getDepartment().getId()
            );

            dto.setDepartmentName(
                    admission.getDepartment().getDepartmentName()
            );
        }

        // ==========================================================
        // Course Details
        // ==========================================================

        if (admission.getCourse() != null) {

            dto.setCourseId(
                    admission.getCourse().getId()
            );

            dto.setCourseName(
                    admission.getCourse().getCourseName()
            );
        }

        return dto;
    }

    // ==========================================================
    // Update Existing Entity
    // ==========================================================

    public void updateEntity(
            Admission admission,
            AdmissionRequestDto dto
    ) {

        admission.setAcademicYear(
                dto.getAcademicYear().trim()
        );


        admission.setAdmissionDate(
                dto.getAdmissionDate()
        );

        admission.setRemarks(
                dto.getRemarks()
        );

    }

}