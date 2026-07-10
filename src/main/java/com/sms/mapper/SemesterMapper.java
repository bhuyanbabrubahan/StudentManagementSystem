package com.sms.mapper;


import org.springframework.stereotype.Component;

import com.sms.dto.SemesterRequestDto;
import com.sms.dto.SemesterResponseDto;
import com.sms.entity.Semester;


@Component
public class SemesterMapper {


    // DTO -> Entity
    public Semester toEntity(SemesterRequestDto dto) {

        Semester semester = new Semester();

        semester.setSemesterName(dto.getSemesterName());

        semester.setSemesterNumber(dto.getSemesterNumber());

        return semester;
    }



    // Entity -> Response DTO
    public SemesterResponseDto toDto(Semester semester) {

        SemesterResponseDto dto = new SemesterResponseDto();


        dto.setId(semester.getId());

        dto.setSemesterName(
                semester.getSemesterName()
        );


        dto.setSemesterNumber(
                semester.getSemesterNumber()
        );


        dto.setStatus(
                semester.getStatus().name()
        );


        if(semester.getCourse()!=null){

            dto.setCourseId(
                    semester.getCourse().getId()
            );


            dto.setCourseName(
                    semester.getCourse().getCourseName()
            );
        }


        return dto;
    }



    // Update existing Entity
    public void updateEntity(
            Semester semester,
            SemesterRequestDto dto
    ){

        semester.setSemesterName(
                dto.getSemesterName()
        );


        semester.setSemesterNumber(
                dto.getSemesterNumber()
        );

    }

}