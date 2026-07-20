package com.sms.dashboard.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admission.entity.AdmissionStatus;
import com.admission.repository.AdmissionRepository;
import com.sms.dashboard.service.DashboardService;
import com.sms.dto.DashboardResponseDto;
import com.sms.enums.AttendanceStatus;
import com.sms.enums.RecordStatus;
import com.sms.enums.ResultStatus;
import com.sms.repository.AttendanceRepository;
import com.sms.repository.CourseRepository;
import com.sms.repository.DepartmentRepository;
import com.sms.repository.ExamRepository;
import com.sms.repository.FacultyRepository;
import com.sms.repository.FacultySubjectRepository;
import com.sms.repository.ResultRepository;
import com.sms.repository.SemesterRepository;
import com.sms.repository.StudentRepository;
import com.sms.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

	private final StudentRepository studentRepository;

	private final DepartmentRepository departmentRepository;

	private final CourseRepository courseRepository;

	private final SemesterRepository semesterRepository;

	private final SubjectRepository subjectRepository;

	private final FacultyRepository facultyRepository;

	private final FacultySubjectRepository facultySubjectRepository;

	private final AdmissionRepository admissionRepository;

	private final AttendanceRepository attendanceRepository;

	private final ExamRepository examRepository;

	private final ResultRepository resultRepository;

	
	
	@Override
	public DashboardResponseDto getDashboard() {

	    DashboardResponseDto dto = new DashboardResponseDto();

	    LocalDate today = LocalDate.now();

	    // ==========================================================
	    // Student Statistics
	    // ==========================================================

	    dto.setTotalStudents(
	            studentRepository.count()
	    );

	    dto.setActiveStudents(
	            studentRepository.countByStatus(
	                    RecordStatus.ACTIVE
	            )
	    );

	    dto.setInactiveStudents(
	            studentRepository.countByStatus(
	                    RecordStatus.INACTIVE
	            )
	    );



	    // ==========================================================
	    // Department Statistics
	    // ==========================================================

	    dto.setTotalDepartments(
	            departmentRepository.countByStatusNot(
	                    RecordStatus.DELETED
	            )
	    );



	    // ==========================================================
	    // Course Statistics
	    // ==========================================================

	    dto.setTotalCourses(
	            courseRepository.countByStatusNot(
	                    RecordStatus.DELETED
	            )
	    );



	    // ==========================================================
	    // Semester Statistics
	    // ==========================================================

	    dto.setTotalSemesters(
	            semesterRepository.countByStatusNot(
	                    RecordStatus.DELETED
	            )
	    );



	    // ==========================================================
	    // Subject Statistics
	    // ==========================================================

	    dto.setTotalSubjects(
	            subjectRepository.countByStatusNot(
	                    RecordStatus.DELETED
	            )
	    );



	    // ==========================================================
	    // Faculty Statistics
	    // ==========================================================

	    dto.setTotalFaculties(
	            facultyRepository.countByStatusNot(
	                    RecordStatus.DELETED
	            )
	    );



	    // ==========================================================
	    // Faculty Subject Mapping Statistics
	    // ==========================================================

	    dto.setTotalFacultySubjects(
	            facultySubjectRepository.countByStatusNot(
	                    RecordStatus.DELETED
	            )
	    );



	    // ==========================================================
	    // Admission Statistics
	    // ==========================================================

	    dto.setTotalAdmissions(
	            admissionRepository.count()
	    );

	    dto.setActiveAdmissions(
	            admissionRepository.countByAdmissionStatus(
	                    AdmissionStatus.ACTIVE
	            )
	    );

	    dto.setCancelledAdmissions(
	            admissionRepository.countByAdmissionStatus(
	                    AdmissionStatus.CANCELLED
	            )
	    );

	    dto.setCompletedAdmissions(
	            admissionRepository.countByAdmissionStatus(
	                    AdmissionStatus.COMPLETED
	            )
	    );



	    // ==========================================================
	    // Attendance Statistics
	    // ==========================================================

	    dto.setTotalAttendance(
	            attendanceRepository.countByStatusNot(
	                    RecordStatus.DELETED
	            )
	    );

	    dto.setTodayAttendance(
	            attendanceRepository.countByAttendanceDateAndStatusNot(
	                    today,
	                    RecordStatus.DELETED
	            )
	    );

	    dto.setPresentStudents(
	            attendanceRepository.countByAttendanceStatusAndAttendanceDateAndStatusNot(
	                    AttendanceStatus.PRESENT,
	                    today,
	                    RecordStatus.DELETED
	            )
	    );

	    dto.setAbsentStudents(
	            attendanceRepository.countByAttendanceStatusAndAttendanceDateAndStatusNot(
	                    AttendanceStatus.ABSENT,
	                    today,
	                    RecordStatus.DELETED
	            )
	    );

	    dto.setLeaveStudents(
	            attendanceRepository.countByAttendanceStatusAndAttendanceDateAndStatusNot(
	                    AttendanceStatus.LEAVE,
	                    today,
	                    RecordStatus.DELETED
	            )
	    );



	    // ==========================================================
	    // Exam Statistics
	    // ==========================================================

	    dto.setTotalExams(
	            examRepository.countByStatusNot(
	                    RecordStatus.DELETED
	            )
	    );

	    dto.setUpcomingExams(
	            examRepository.countByExamDateAfterAndStatusNot(
	                    today,
	                    RecordStatus.DELETED
	            )
	    );

	    dto.setCompletedExams(
	            examRepository.countByExamDateBeforeAndStatusNot(
	                    today,
	                    RecordStatus.DELETED
	            )
	    );



	    // ==========================================================
	    // Result Statistics
	    // ==========================================================

	    dto.setTotalResults(
	            resultRepository.countByStatusNot(
	                    RecordStatus.DELETED
	            )
	    );

	    dto.setPassResults(
	            resultRepository.countByResultStatusAndStatusNot(
	                    ResultStatus.PASS,
	                    RecordStatus.DELETED
	            )
	    );

	    dto.setFailResults(
	            resultRepository.countByResultStatusAndStatusNot(
	                    ResultStatus.FAIL,
	                    RecordStatus.DELETED
	            )
	    );



	    BigDecimal average =
	            resultRepository.findAveragePercentage(
	                    RecordStatus.DELETED
	            );

	    dto.setAveragePercentage(
	            average == null
	                    ? BigDecimal.ZERO
	                    : average
	    );

	    return dto;
	}
}