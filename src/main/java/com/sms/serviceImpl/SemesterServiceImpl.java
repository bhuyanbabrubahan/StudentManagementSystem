package com.sms.serviceImpl;
import java.time.temporal.ChronoUnit;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.dto.PageResponse;
import com.sms.dto.SemesterRequestDto;
import com.sms.dto.SemesterResponseDto;
import com.sms.dto.SemesterSearchRequest;

import com.sms.entity.Course;
import com.sms.entity.Semester;
import com.sms.enums.CourseStatus;
import com.sms.enums.SemesterStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.ResourceNotFoundException;

import com.sms.mapper.SemesterMapper;

import com.sms.repository.CourseRepository;
import com.sms.repository.SemesterRepository;

import com.sms.service.SemesterService;
import com.sms.specification.SemesterSpecification;

@Service
@Transactional
public class SemesterServiceImpl implements SemesterService {


    private final SemesterRepository repository;
    private final SemesterMapper mapper;
    private final CourseRepository courseRepository;

    public SemesterServiceImpl(
            SemesterRepository repository,
            SemesterMapper mapper,
            CourseRepository courseRepository
    ) {

        this.repository = repository;
        this.mapper = mapper;
        this.courseRepository = courseRepository;
    }


   
    // ================= CREATE =================

    @Override
    @Transactional
    public SemesterResponseDto createSemester(
            SemesterRequestDto dto
    ) {


        // 1. Common Validation

		validateSemester(dto, null, false);

        

        // 2. Fetch ACTIVE Course

        Course course =
                courseRepository
                .findByIdAndStatus(
                        dto.getCourseId(),
                        CourseStatus.ACTIVE
                )
                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Course not found with id: "
                                + dto.getCourseId()
                        )
                );

		// 3. DTO -> Entity

		Semester semester = mapper.toEntity(dto);

		// 4. Relationship

		semester.setCourse(course);

		// 5. Default Status

		semester.setStatus(SemesterStatus.ACTIVE);

		// 6. Save

		Semester saved = repository.save(semester);

		// 7. Response

		return mapper.toDto(saved);

    }





    // ================= GET BY ID =================


    @Override
    public SemesterResponseDto getSemesterById(
            Long id
    ) {


        Semester semester =
                repository
                .findByIdAndStatusNot(
                        id,
                        SemesterStatus.DELETED
                )
                .orElseThrow(() ->

                    new ResourceNotFoundException(
                       "Semester not found with id: "
                       + id
                    )

                );



        return mapper.toDto(semester);

    }






    // ================= UPDATE =================


 // ================= UPDATE =================

    @Override
    @Transactional
    public SemesterResponseDto updateSemester(
            Long id,
            SemesterRequestDto dto
    ) {


        // 1. Existing Semester Fetch

        Semester semester =
                repository
                .findByIdAndStatusNot(
                        id,
                        SemesterStatus.DELETED
                )
                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Semester not found with id: "
                                + id
                        )
                );



        // 2. Common Validation

        validateSemester(
                dto,
                id,
                true
        );



        // 3. Fetch ACTIVE Course

        Course course =
                courseRepository
                .findByIdAndStatus(
                        dto.getCourseId(),
                        CourseStatus.ACTIVE
                )
                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Course not found with id: "
                                + dto.getCourseId()
                        )
                );

		// 4. Update Fields
		mapper.updateEntity(semester, dto);

		// 5. Update Relationship
		semester.setCourse(course);

		// 6. Save
		Semester updated = repository.save(semester);

		// 7. Response
		return mapper.toDto(updated);

    }







    // ================= DELETE =================


    @Override
    public void deleteSemester(Long id) {



        Semester semester =
                repository
                .findByIdAndStatusNot(
                        id,
                        SemesterStatus.DELETED
                )
                .orElseThrow(() ->

                    new ResourceNotFoundException(
                      "Semester not found with id: "
                      + id
                    )
                );



        semester.setStatus(
                SemesterStatus.DELETED
        );



        repository.save(semester);

    }








    // ================= GET ALL =================


    @Override
    public PageResponse<SemesterResponseDto> getAllSemesters(
            int page,
            int size,
            String sortBy,
            String direction
    ) {



        Sort sort =
                direction.equalsIgnoreCase("asc")
                ?
                Sort.by(sortBy).ascending()
                :
                Sort.by(sortBy).descending();



        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );



        Page<Semester> semesterPage =
                repository.findByStatusNot(
                        SemesterStatus.DELETED,
                        pageable
                );



        List<SemesterResponseDto> content =
                semesterPage.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();




        PageResponse<SemesterResponseDto> response =
                new PageResponse<>();


        response.setContent(content);

        response.setPageNumber(
                semesterPage.getNumber()
        );

        response.setPageSize(
                semesterPage.getSize()
        );

        response.setTotalElements(
                semesterPage.getTotalElements()
        );

        response.setTotalPages(
                semesterPage.getTotalPages()
        );

        response.setLast(
                semesterPage.isLast()
        );



        return response;

    }






    // ================= SEARCH =================


    @Override
    public PageResponse<SemesterResponseDto> searchSemesters(
            SemesterSearchRequest request,
            int page,
            int size,
            String sortBy,
            String direction
    ) {



        Specification<Semester> spec =
                Specification.where(null);



        if(request.getStatus()!=null) {

            spec =
            spec.and(
              SemesterSpecification.hasStatus(
                      request.getStatus()
              )
            );


        }else {


            spec =
            spec.and(
              SemesterSpecification.hasStatus(
                      SemesterStatus.ACTIVE
              )
            );

        }





        if(request.getSemesterName()!=null &&
           !request.getSemesterName().isBlank()) {


            spec =
            spec.and(
              SemesterSpecification.hasSemesterName(
                      request.getSemesterName()
              )
            );

        }




        if(request.getSemesterNumber()!=null) {


            spec =
            spec.and(
              SemesterSpecification.hasSemesterNumber(
                      request.getSemesterNumber()
              )
            );

        }





        if(request.getCourseId()!=null) {


            spec =
            spec.and(
              SemesterSpecification.hasCourse(
                      request.getCourseId()
              )
            );

        }






        Sort sort =
                direction.equalsIgnoreCase("asc")
                ?
                Sort.by(sortBy).ascending()
                :
                Sort.by(sortBy).descending();




        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );




        Page<Semester> pageResult =
                repository.findAll(
                        spec,
                        pageable
                );




        List<SemesterResponseDto> content =
                pageResult.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();




        PageResponse<SemesterResponseDto> response =
                new PageResponse<>();


        response.setContent(content);
        response.setPageNumber(pageResult.getNumber());
        response.setPageSize(pageResult.getSize());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setLast(pageResult.isLast());


        return response;

    }

    
    private void validateSemester(
            SemesterRequestDto dto,
            Long id,
            boolean update
    ) {


        // ==========================
        // 1. Date Validation
        // ==========================


        if(dto.getSemesterStartDate()
                .isAfter(dto.getSemesterEndDate())) {


            throw new BusinessException(
                    "Semester start date cannot be after end date."
            );
        }



        if(dto.getSemesterStartDate()
                .isEqual(dto.getSemesterEndDate())) {


            throw new BusinessException(
                    "Semester start date and end date cannot be same."
            );
        }



        // ==========================
        // 2. Working Days Validation
        // ==========================


        if(dto.getTotalWorkingDays() == null ||
                dto.getTotalWorkingDays() <= 0) {


            throw new BusinessException(
                    "Total working days must be greater than zero."
            );
        }



        long semesterDuration =

                ChronoUnit.DAYS.between(
                        dto.getSemesterStartDate(),
                        dto.getSemesterEndDate()
                ) + 1;



        if(dto.getTotalWorkingDays()
                > semesterDuration) {


            throw new BusinessException(
                    "Working days cannot exceed semester duration."
            );
        }



        // ==========================
        // 3. Duplicate Semester Number
        // ==========================


        boolean duplicateSemester;



        if(update) {


            duplicateSemester =
                    repository
                    .existsByCourseIdAndSemesterNumberAndIdNot(
                            dto.getCourseId(),
                            dto.getSemesterNumber(),
                            id
                    );


        } else {


            duplicateSemester =
                    repository
                    .existsByCourseIdAndSemesterNumber(
                            dto.getCourseId(),
                            dto.getSemesterNumber()
                    );

        }



        if(duplicateSemester) {


            throw new BusinessException(
                    "Semester number already exists for this course."
            );
        }



        // ==========================
        // 4. Date Overlap Validation
        // ==========================


        boolean overlap;



        if(update) {


            overlap =
                    repository.existsSemesterOverlapForUpdate(
                            id,
                            dto.getCourseId(),
                            dto.getSemesterStartDate(),
                            dto.getSemesterEndDate()
                    );


        } else {


            overlap =
                    repository.existsSemesterOverlap(
                            dto.getCourseId(),
                            dto.getSemesterStartDate(),
                            dto.getSemesterEndDate()
                    );

        }



        if(overlap) {


            throw new BusinessException(
                    "Semester date range overlaps with existing semester."
            );
        }

    }

}