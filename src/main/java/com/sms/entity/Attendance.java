package com.sms.entity;

import java.time.LocalDate;

import com.sms.enums.AttendanceRecordStatus;
import com.sms.enums.AttendanceStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "attendance",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_student_subject_date",
            columnNames = {
                "student_id",
                "faculty_subject_mapping_id",
                "attendance_date"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many Attendance -> One Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    @ToString.Exclude
    private Student student;

    // Many Attendance -> One FacultySubjectMapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "faculty_subject_mapping_id",
            nullable = false
    )
    @ToString.Exclude
    private FacultySubjectMapping facultySubjectMapping;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus attendanceStatus;

    @Column(length = 300)
    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceRecordStatus status;

}