package com.sms.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sms.dto.SemesterRequestDto;
import com.sms.dto.SemesterResponseDto;
import com.sms.entity.Semester;

@Mapper(componentModel = "spring")
public interface SemesterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    Semester toEntity(SemesterRequestDto dto);

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.courseName", target = "courseName")
    SemesterResponseDto toDto(Semester semester);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    void updateEntity(
            @MappingTarget Semester semester,
            SemesterRequestDto dto
    );

}