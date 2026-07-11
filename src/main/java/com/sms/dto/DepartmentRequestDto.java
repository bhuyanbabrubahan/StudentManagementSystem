package com.sms.dto;

import com.sms.enums.DepartmentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDto {

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Department name cannot exceed 100 characters")
    private String departmentName;

    @NotBlank(message = "Department code is required")
    @Size(max = 20, message = "Department code cannot exceed 20 characters")
    private String departmentCode;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
    
	private DepartmentStatus status;

}