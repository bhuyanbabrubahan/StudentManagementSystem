package com.sms.mapper;

import org.springframework.stereotype.Component;

import com.sms.dto.SubjectRequestDto;
import com.sms.dto.SubjectResponseDto;
import com.sms.entity.Subject;

@Component
public class SubjectMapper {

    // Request DTO -> Entity
    public Subject toEntity(SubjectRequestDto dto) {

        Subject subject = new Subject();

        subject.setSubjectName(dto.getSubjectName());
        subject.setCredits(dto.getCredits());
        subject.setTheoryMarks(dto.getTheoryMarks());
        subject.setPracticalMarks(dto.getPracticalMarks());

        return subject;
    }

    // Entity -> Response DTO
    public SubjectResponseDto toDto(Subject subject) {

        SubjectResponseDto dto = new SubjectResponseDto();

        dto.setId(subject.getId());
        dto.setSubjectCode(subject.getSubjectCode());
        dto.setSubjectName(subject.getSubjectName());
        dto.setCredits(subject.getCredits());
        dto.setTheoryMarks(subject.getTheoryMarks());
        dto.setPracticalMarks(subject.getPracticalMarks());
        dto.setStatus(subject.getStatus());

        if (subject.getSemester() != null) {

            dto.setSemesterId(subject.getSemester().getId());
            dto.setSemesterName(subject.getSemester().getSemesterName());
        }

        return dto;
    }

    // Update Existing Entity
    public void updateEntity(
            Subject subject,
            SubjectRequestDto dto) {

        subject.setSubjectName(dto.getSubjectName());
        subject.setCredits(dto.getCredits());
        subject.setTheoryMarks(dto.getTheoryMarks());
        subject.setPracticalMarks(dto.getPracticalMarks());
    }

}