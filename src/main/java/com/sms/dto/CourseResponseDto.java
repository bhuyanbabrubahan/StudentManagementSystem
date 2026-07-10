package com.sms.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sms.enums.CourseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

    private Long id;

    private String courseCode;

    private String courseName;

    private Integer durationInMonths;

    private BigDecimal fees;

    private String description;

    private CourseStatus status;

    // 🔥 relationship output (important for frontend)
    private Long departmentId;

    private String departmentName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}