package com.sms.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.dto.AttendanceRequestDto;
import com.sms.dto.AttendanceResponseDto;
import com.sms.dto.AttendanceSearchRequest;
import com.sms.dto.PageResponse;
import com.sms.entity.Attendance;
import com.sms.entity.FacultySubjectMapping;
import com.sms.entity.Semester;
import com.sms.entity.Student;
import com.sms.enums.AttendanceRecordStatus;
import com.sms.enums.FacultySubjectStatus;
import com.sms.enums.StudentStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.mapper.AttendanceMapper;
import com.sms.repository.AttendanceRepository;
import com.sms.repository.FacultySubjectRepository;
import com.sms.repository.StudentRepository;
import com.sms.service.AttendanceService;
import com.sms.specification.AttendanceSpecification;

import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {


    private final AttendanceRepository repository;

    private final StudentRepository studentRepository;

    private final FacultySubjectRepository facultySubjectRepository;

    private final AttendanceMapper mapper;
    

    @Override
    public AttendanceResponseDto createAttendance(
            AttendanceRequestDto dto) {


        // 1. Duplicate Attendance Check
        boolean exists =
                repository
                .existsByStudentIdAndFacultySubjectMappingIdAndAttendanceDateAndStatusNot(
                        dto.getStudentId(),
                        dto.getFacultySubjectMappingId(),
                        dto.getAttendanceDate(),
                        AttendanceRecordStatus.DELETED
                );


        if (exists) {

            throw new BusinessException(
                    "Attendance already marked for this student on this date."
            );
        }


        // 2. Find ACTIVE Student
        Student student =
                studentRepository
                .findByIdAndStatusNot(
                        dto.getStudentId(),
                        StudentStatus.DELETED
                )
                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Student not found with id : "
                                + dto.getStudentId()
                        )
                );



        // 3. Find ACTIVE Faculty Subject Mapping
        FacultySubjectMapping mapping =
                facultySubjectRepository
                .findByIdAndStatusNot(
                        dto.getFacultySubjectMappingId(),
                        FacultySubjectStatus.DELETED
                )
                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Faculty Subject Mapping not found with id : "
                                + dto.getFacultySubjectMappingId()
                        )
                );

        // 4. Business Validation

        validateAttendance(
                dto,
                student,
                mapping
        );

        // 5. DTO -> Entity
        Attendance attendance =
                mapper.toEntity(dto);


        // 6. Set Relationship
        attendance.setStudent(student);

        attendance.setFacultySubjectMapping(mapping);



        // 7. Default Record Status
        attendance.setStatus(
                AttendanceRecordStatus.ACTIVE
        );



        // 8. Save
        Attendance saved =
                repository.save(attendance);


        // 9. Entity -> Response DTO
        return mapper.toDto(saved);

    }

	
    
    
    
    @Override
    @Transactional(readOnly = true)
    public AttendanceResponseDto getAttendanceById(Long id) {

        // 1. Find Active Attendance
        Attendance attendance =
                repository
                .findByIdAndStatusNot(
                        id,
                        AttendanceRecordStatus.DELETED
                )
                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Attendance not found with id : "
                                + id
                        )
                );


        // 2. Entity -> DTO
        return mapper.toDto(attendance);

    }
    
    
    
    

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AttendanceResponseDto> getAllAttendance(
            int page,
            int size,
            String sortBy,
            String direction) {


      
        // 1. Sorting
        Sort sort =
                direction.equalsIgnoreCase("asc")

                ? Sort.by(sortBy).ascending()

                : Sort.by(sortBy).descending();



        // 2. Pageable
        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );



        // 3. Fetch Non Deleted Data

        Page<Attendance> result =
                repository.findByStatusNot(
                        AttendanceRecordStatus.DELETED,
                        pageable
                );



        // 4. Convert Entity -> DTO

        List<AttendanceResponseDto> content =
                result
                .getContent()
                .stream()
                .map(mapper::toDto)
                .toList();



        // 5. Prepare Response

        PageResponse<AttendanceResponseDto> response =
                new PageResponse<>();


        response.setContent(content);

        response.setPageNumber(
                result.getNumber()
        );

        response.setPageSize(
                result.getSize()
        );

        response.setTotalElements(
                result.getTotalElements()
        );

        response.setTotalPages(
                result.getTotalPages()
        );

        response.setLast(
                result.isLast()
        );


        return response;

    }
    
    
    
    
    

    @Override
    public AttendanceResponseDto updateAttendance(
            Long id,
            AttendanceRequestDto dto) {


        // 1. Fetch Existing Attendance
        Attendance attendance =
                repository
                .findByIdAndStatusNot(
                        id,
                        AttendanceRecordStatus.DELETED
                )
                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Attendance not found with id : "
                                + id
                        )
                );



        // 2. Duplicate Validation
        boolean exists =
                repository
                .existsByStudentIdAndFacultySubjectMappingIdAndAttendanceDateAndStatusNotAndIdNot(
                        dto.getStudentId(),
                        dto.getFacultySubjectMappingId(),
                        dto.getAttendanceDate(),
                        AttendanceRecordStatus.DELETED,
                        id
                );


        if (exists) {

            throw new BusinessException(
                    "Attendance already exists for this student on this date."
            );
        }


        // 3. Validate Student
        Student student =
                studentRepository
                .findByIdAndStatusNot(
                        dto.getStudentId(),
                        StudentStatus.DELETED
                )
                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Student not found with id : "
                                + dto.getStudentId()
                        )
                );



        // 4. Validate Faculty Subject
        FacultySubjectMapping mapping =
                facultySubjectRepository
                .findByIdAndStatusNot(
                        dto.getFacultySubjectMappingId(),
                        FacultySubjectStatus.DELETED
                )
                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Faculty Subject Mapping not found with id : "
                                + dto.getFacultySubjectMappingId()
                        )
                );
        
        // 5. Business Validation
        validateAttendance(
                dto,
                student,
                mapping
        );

        // 6. Update Simple Fields
        mapper.updateEntity(
                attendance,
                dto
        );

        // 7. Update Relationship
        attendance.setStudent(student);

        attendance.setFacultySubjectMapping(mapping);

        // 8. Save Updated Data
        Attendance updated =
                repository.save(attendance);

        // 9. Return Response
        return mapper.toDto(updated);

    }
    
    
    
    @Override
    public void deleteAttendance(Long id) {


        // 1. Find Existing Attendance
        Attendance attendance =
                repository
                .findByIdAndStatusNot(
                        id,
                        AttendanceRecordStatus.DELETED
                )
                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Attendance not found with id : "
                                + id
                        )
                );


        // 2. Soft Delete
        attendance.setStatus(
                AttendanceRecordStatus.DELETED
        );

        // 3. Save
        repository.save(attendance);

    }

	

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AttendanceResponseDto> searchAttendance(
            AttendanceSearchRequest request,
            int page,
            int size,
            String sortBy,
            String direction) {


        Specification<Attendance> spec =
                Specification.where(null);



        // ==========================
        // Default Active Filter
        // ==========================

        if(request.getStatus()!=null){

            spec =
            spec.and(
            AttendanceSpecification.hasStatus(
                    request.getStatus()
            ));

        }
        else{

            spec =
            spec.and(
            AttendanceSpecification.statusNot(
                    AttendanceRecordStatus.DELETED
            ));

        }



        // Student

        if(request.getStudentId()!=null){

            spec =
            spec.and(
            AttendanceSpecification.hasStudent(
                    request.getStudentId()
            ));

        }



        // Faculty Subject

        if(request.getFacultySubjectMappingId()!=null){

            spec =
            spec.and(
            AttendanceSpecification.hasFacultySubject(
                    request.getFacultySubjectMappingId()
            ));

        }


        if(request.getFacultyId()!=null){

            spec =
            spec.and(
            AttendanceSpecification.hasFaculty(
                    request.getFacultyId()
            ));

        }



        if(request.getSubjectId()!=null){

            spec =
            spec.and(
            AttendanceSpecification.hasSubject(
                    request.getSubjectId()
            ));

        }

        // Date

        if(request.getAttendanceDate()!=null){

            spec =
            spec.and(
            AttendanceSpecification.hasDate(
                    request.getAttendanceDate()
            ));

        }



        // Present/Absent

        if(request.getAttendanceStatus()!=null){

            spec =
            spec.and(
            AttendanceSpecification.hasAttendanceStatus(
                    request.getAttendanceStatus()
            ));

        }



        Sort sort =
                direction.equalsIgnoreCase("asc")

                ? Sort.by(sortBy).ascending()

                : Sort.by(sortBy).descending();



        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );



        Page<Attendance> result =
                repository.findAll(
                        spec,
                        pageable
                );



        List<AttendanceResponseDto> content =
                result.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();



        PageResponse<AttendanceResponseDto> response =
                new PageResponse<>();


        response.setContent(content);

        response.setPageNumber(result.getNumber());

        response.setPageSize(result.getSize());

        response.setTotalElements(result.getTotalElements());

        response.setTotalPages(result.getTotalPages());

        response.setLast(result.isLast());


        return response;

    }

    
    private void validateAttendance(
            AttendanceRequestDto dto,
            Student student,
            FacultySubjectMapping mapping) {

    	if(dto.getAttendanceDate()==null){

    	    throw new BusinessException(
    	            "Attendance date is required."
    	    );

    	}

        LocalDate attendanceDate =
                dto.getAttendanceDate();



        // ==========================
        // Future Date Validation
        // ==========================

        if(attendanceDate.isAfter(LocalDate.now())){


            throw new BusinessException(
                    "Attendance cannot be marked for future date."
            );

        }



        // ==========================
        // Student Admission Validation
        // ==========================

        if(student.getAdmissionDate()!=null &&
                attendanceDate.isBefore(
                        student.getAdmissionDate())){


            throw new BusinessException(
                    "Attendance cannot be before student admission date."
            );

        }




        // ==========================
        // Faculty Assignment Validation
        // ==========================

        if(mapping.getAssignedDate()!=null &&
                attendanceDate.isBefore(
                        mapping.getAssignedDate())){


            throw new BusinessException(
                    "Attendance cannot be before faculty assignment date."
            );

        }




        // ==========================
        // Semester Validation
        // ==========================


        if(mapping.getSubject()==null ||
           mapping.getSubject().getSemester()==null){


            throw new BusinessException(
                    "Semester information not found for subject."
            );

        }



        Semester semester =
                mapping.getSubject()
                       .getSemester();


        // ==========================
        // Semester Date Null Check
        // ==========================

        if(semester.getSemesterStartDate()==null ||
           semester.getSemesterEndDate()==null){


            throw new BusinessException(
                    "Semester date information missing."
            );

        }



        // ==========================
        // Semester Duration Validation
        // ==========================

        if(attendanceDate.isBefore(
                semester.getSemesterStartDate())){


            throw new BusinessException(
                    "Attendance cannot be before semester start date."
            );

        }



        if(attendanceDate.isAfter(
                semester.getSemesterEndDate())){


            throw new BusinessException(
                    "Attendance cannot be after semester end date."
            );

        }




        if(attendanceDate.isAfter(
                semester.getSemesterEndDate())){


            throw new BusinessException(
                    "Attendance cannot be after semester end date."
            );

        }


    }
    
    
    

}