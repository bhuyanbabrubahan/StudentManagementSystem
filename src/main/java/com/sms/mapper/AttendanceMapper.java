package com.sms.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sms.dto.AttendanceRequestDto;
import com.sms.dto.AttendanceResponseDto;
import com.sms.entity.Attendance;



@Mapper(componentModel = "spring")
public interface AttendanceMapper {



    // ==========================
    // DTO -> ENTITY
    // ==========================


    @Mapping(
        target = "id",
        ignore = true
    )

    @Mapping(
        target = "student",
        ignore = true
    )

    @Mapping(
        target = "facultySubjectMapping",
        ignore = true
    )


    @Mapping(
        target = "status",
        ignore = true
    )

    Attendance toEntity(
            AttendanceRequestDto dto
    );





    // ==========================
    // ENTITY -> DTO
    // ==========================


    @Mapping(
        source = "student.id",
        target = "studentId"
    )


    @Mapping(
        expression =
        "java(attendance.getStudent().getFirstName() + \" \" + attendance.getStudent().getLastName())",
        target = "studentName"
    )


    @Mapping(
        source = "facultySubjectMapping.id",
        target = "facultySubjectMappingId"
    )


    @Mapping(
        expression =
        "java(attendance.getFacultySubjectMapping().getFaculty().getFirstName() + \" \" + attendance.getFacultySubjectMapping().getFaculty().getLastName())",
        target = "facultyName"
    )


    @Mapping(
        source =
        "facultySubjectMapping.subject.subjectName",
        target = "subjectName"
    )


    AttendanceResponseDto toDto(
            Attendance attendance
    );





    // ==========================
    // UPDATE
    // ==========================


    @BeanMapping(
        nullValuePropertyMappingStrategy =
        NullValuePropertyMappingStrategy.IGNORE
    )


    @Mapping(
        target="id",
        ignore=true
    )


    @Mapping(
        target="student",
        ignore=true
    )


    @Mapping(
        target="facultySubjectMapping",
        ignore=true
    )


    @Mapping(
        target="status",
        ignore=true
    )


    void updateEntity(
            @MappingTarget Attendance attendance,
            AttendanceRequestDto dto
    );


}