package com.sms.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "DashboardResponseDto",
        description = "Dashboard summary information."
)
public class DashboardResponseDto {

    // ==========================
    // Student Statistics
    // ==========================

    private Long totalStudents;
    private Long activeStudents;
    private Long inactiveStudents;

    // ==========================
    // Department Statistics
    // ==========================

    private Long totalDepartments;

    // ==========================
    // Course Statistics
    // ==========================

    private Long totalCourses;

    // ==========================
    // Semester Statistics
    // ==========================

    private Long totalSemesters;

    // ==========================
    // Subject Statistics
    // ==========================

    private Long totalSubjects;

    // ==========================
    // Faculty Statistics
    // ==========================

    private Long totalFaculties;

    // ==========================
    // Faculty Subject Statistics
    // ==========================

    private Long totalFacultySubjects;

    // ==========================
    // Admission Statistics
    // ==========================

    private Long totalAdmissions;
    private Long activeAdmissions;
    private Long cancelledAdmissions;
    private Long completedAdmissions;

    // ==========================
    // Attendance Statistics
    // ==========================

    private Long totalAttendance;
    private Long todayAttendance;
    private Long presentStudents;
    private Long absentStudents;
    private Long leaveStudents;

    // ==========================
    // Exam Statistics
    // ==========================

    private Long totalExams;
    private Long upcomingExams;
    private Long completedExams;

    // ==========================
    // Result Statistics
    // ==========================

    private Long totalResults;
    private Long passResults;
    private Long failResults;
    private BigDecimal averagePercentage;
    
    

}