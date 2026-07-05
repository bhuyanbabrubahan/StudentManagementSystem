package com.example.student.dto;

import java.time.LocalDateTime;

import com.example.student.entity.DepartmentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDto {

    private Long id;

    private String departmentName;

    private String departmentCode;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    private DepartmentStatus status;
}
