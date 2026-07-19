package com.sms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sms.dto.ResultRequestDto;
import com.sms.dto.ResultResponseDto;
import com.sms.entity.Result;

@Mapper(componentModel = "spring")
public interface ResultMapper {

    // Request DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "exam", ignore = true)
    @Mapping(target = "percentage", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "resultStatus", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Result toEntity(ResultRequestDto dto);


    // Entity -> Response DTO
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.rollNumber", target = "rollNumber")
    @Mapping(source = "student.fullName", target = "studentName")

    @Mapping(source = "exam.id", target = "examId")
    @Mapping(source = "exam.examName", target = "examName")

    @Mapping(source = "exam.subject.subjectName", target = "subjectName")
    @Mapping(source = "exam.subject.id", target = "subjectId")

    @Mapping(source = "exam.semester.id", target = "semesterId")
    @Mapping(source = "exam.semester.semesterName", target = "semesterName")

    @Mapping(source = "exam.maximumMarks", target = "maximumMarks")

    ResultResponseDto toResponseDto(Result result);

}