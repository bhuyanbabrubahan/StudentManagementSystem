package com.sms.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sms.dto.studentdocument.StudentDocumentResponseDto;
import com.sms.entity.StudentDocument;



@Mapper(
        componentModel = "spring"
)
public interface StudentDocumentMapper {



    @Mapping(
            source = "student.id",
            target = "studentId"
    )
    StudentDocumentResponseDto convertToDto(
            StudentDocument studentDocument
    );



    List<StudentDocumentResponseDto> convertToDtoList(

            List<StudentDocument> documents

    );



}