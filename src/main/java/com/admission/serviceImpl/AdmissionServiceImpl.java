package com.admission.serviceImpl;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admission.dto.AdmissionRequestDto;
import com.admission.dto.AdmissionResponseDto;
import com.admission.dto.AdmissionSearchRequest;
import com.admission.entity.Admission;
import com.admission.entity.AdmissionStatus;
import com.admission.mapper.AdmissionMapper;
import com.admission.repository.AdmissionRepository;
import com.admission.service.AdmissionService;
import com.admission.specification.AdmissionSpecification;
import com.sms.dto.PageResponse;
import com.sms.entity.Course;
import com.sms.entity.Department;
import com.sms.entity.Student;
import com.sms.enums.RecordStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.repository.CourseRepository;
import com.sms.repository.DepartmentRepository;
import com.sms.repository.StudentRepository;
import com.sms.sequence.enums.ModuleType;
import com.sms.sequence.service.CodeGeneratorService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
@Transactional
public class AdmissionServiceImpl implements AdmissionService {


    private final AdmissionRepository admissionRepository;
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final AdmissionMapper admissionMapper;
    private final CodeGeneratorService codeGeneratorService;



    // =====================================================
    // CREATE ADMISSION
    // =====================================================


    @Override
    public AdmissionResponseDto createAdmission(
            AdmissionRequestDto dto) {


    	Student student =
    			getActiveStudent(
    			        dto.getStudentId()
    			);



        validateDuplicateAdmission(
                dto.getStudentId()
        );



        Department department =
        		getActiveDepartment(
        		        dto.getDepartmentId()
        		);



        Course course =
        		getActiveCourse(
        		        dto.getCourseId()
        		);



        validateCourseDepartment(
                course,
                department
        );


        Admission admission =
                admissionMapper.toEntity(dto);


        admission.setStudent(student);

        admission.setDepartment(department);

        admission.setCourse(course);

        admission.setAdmissionNumber(
                codeGeneratorService
                .generateCode(
                        ModuleType.ADMISSION,
                        "ADM"
                )
        );


        admission.setStatus(
                AdmissionStatus.ACTIVE
        );


        if(admission.getAdmissionDate()==null){

            admission.setAdmissionDate(
                    LocalDate.now()
            );
        }


        Admission savedAdmission =
                admissionRepository.save(admission);


        return admissionMapper.toDto(savedAdmission);

    }
    
    
	 // =====================================================
	 // GET ADMISSION BY ID
	 // =====================================================
	
	
	 @Override
	 @Transactional(readOnly = true)
	 public AdmissionResponseDto getAdmissionById(Long id) {
	
	
	     Admission admission =
	             admissionRepository
	             .findByIdAndStatusNot(
	                     id,
	                     AdmissionStatus.CANCELLED
	             )
	             .orElseThrow(() ->
	                     new ResourceNotFoundException(
	                     "Admission not found with id : "
	                     + id
	             ));
	
	
	     return admissionMapper.toDto(admission);
	
	 }
	 
	 
	// =====================================================
	// GET ALL ADMISSIONS WITH PAGINATION
	// =====================================================


	@Override
	@Transactional(readOnly = true)
	public PageResponse<AdmissionResponseDto> getAllAdmissions(
	        int page,
	        int size,
	        String sortBy,
	        String direction) {



	    /*
	     * Step 1:
	     *
	     * Create validated sort object.
	     *
	     * Invalid sort field ya direction
	     * createSort() handle karega.
	     */


	    Sort sort =
	            createSort(
	                    sortBy,
	                    direction
	            );





	    /*
	     * Step 2:
	     *
	     * Pagination object create karna.
	     */


	    Pageable pageable =
	            PageRequest.of(
	                    page,
	                    size,
	                    sort
	            );







	    /*
	     * Step 3:
	     *
	     * Cancelled admission exclude karna.
	     *
	     * Cancelled admissions user ko show nahi karenge.
	     */


	    Page<Admission> admissionPage =
	            admissionRepository
	            .findByStatusNot(
	                    AdmissionStatus.CANCELLED,
	                    pageable
	            );







	    /*
	     * Step 4:
	     *
	     * Entity -> Response DTO conversion
	     */


	    List<AdmissionResponseDto> content =
	            admissionPage
	            .getContent()
	            .stream()
	            .map(admissionMapper::toDto)
	            .toList();




	    /*
	     * Step 5:
	     *
	     * Common pagination response create karna.
	     *
	     * createPageResponse()
	     * reusable helper method hai.
	     */


	    return createPageResponse(
	            admissionPage,
	            content
	    );

	}
	
	
	
	
	// =====================================================
	// UPDATE ADMISSION
	// =====================================================


	@Override
	public AdmissionResponseDto updateAdmission(
	        Long id,
	        AdmissionRequestDto dto) {



	    /*
	     * Step 1:
	     * Existing admission fetch karna.
	     *
	     * Cancelled admission update allowed nahi.
	     */


	    Admission admission =
	            admissionRepository
	            .findByIdAndStatusNot(
	                    id,
	                    AdmissionStatus.CANCELLED
	            )
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                    "Admission not found with id : "
	                    + id
	            ));



	    /*
	     * Step 2:
	     * Student validation
	     *
	     * Deleted student assign nahi ho sakta.
	     */


	    Student student =
	            getActiveStudent(
	                    dto.getStudentId()
	            );


	    /*
	     * Step 3:
	     * Duplicate admission validation
	     *
	     * Same admission ko ignore karega.
	     *
	     * Example:
	     *
	     * Admission ID 1
	     * Student ID 100
	     *
	     * Update same student -> allowed
	     *
	     * Other student already active -> rejected
	     */


	    validateDuplicateAdmissionForUpdate(
	            id,
	            dto.getStudentId()
	    );



	     // Step 4: Department validation


	    Department department =
	            getActiveDepartment(
	                    dto.getDepartmentId()
	            );


	     // Step 5: Course validation

	    Course course =
	            getActiveCourse(
	                    dto.getCourseId()
	            );


	     // Step 6: Course belongs to Department check

	    validateCourseDepartment(
	            course,
	            department
	    );



	    /*
	     * Step 7: Existing admission date preserve karna.
	     * Agar request me date nahi aayi
	     * to purani date maintain hogi.
	     */


	    LocalDate oldAdmissionDate =
	            admission.getAdmissionDate();


	     // Step 8: DTO updated values copy

	    admissionMapper.updateEntity(
	            admission,
	            dto
	    );


	    if(dto.getAdmissionDate()==null){


	        admission.setAdmissionDate(
	                oldAdmissionDate
	        );

	    }


	     // Step 9: Update relationships

	    admission.setStudent(student);

	    admission.setDepartment(department);

	    admission.setCourse(course);

	    // Step 10: Save updated admission

	    Admission updated =
	            admissionRepository.save(admission);


	      //Step 11: Entity -> Response DTO

	    return admissionMapper.toDto(updated);

	}
	
	
	
	// =====================================================
	// DELETE / CANCEL ADMISSION
	// =====================================================


	@Override
	public void deleteAdmission(Long id) {


	    Admission admission =
	            admissionRepository
	            .findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                    "Admission not found with id : "
	                    + id
	            ));


	    if(admission.getStatus()
	            == AdmissionStatus.CANCELLED){


	        throw new BusinessException(
	                "Admission already cancelled"
	        );

	    }


	    if(admission.getStatus()
	            == AdmissionStatus.COMPLETED){


	        throw new BusinessException(
	                "Completed admission cannot be cancelled"
	        );

	    }

	    admission.setStatus(
	            AdmissionStatus.CANCELLED
	    );

	    admissionRepository.save(admission);

	}
	
	
	
	
	// =====================================================
	// SEARCH ADMISSIONS WITH SPECIFICATION
	// =====================================================


	@Override
	@Transactional(readOnly = true)
	public PageResponse<AdmissionResponseDto> searchAdmissions(
	        AdmissionSearchRequest request,
	        int page,
	        int size,
	        String sortBy,
	        String direction) {



	    /*
	     * Step 1:
	     *
	     * Empty Specification create karna.
	     *
	     * Baad me conditions dynamically add hongi.
	     */


	    Specification<Admission> specification =
	            Specification.where(null);







	    /*
	     * Step 2:
	     *
	     * Status filter
	     *
	     * Agar user status bhejta hai
	     * to wahi use hoga.
	     *
	     * Nahi bhejta hai to default ACTIVE.
	     */


	    if(request.getStatus() != null){


	        specification =
	                specification.and(
	                AdmissionSpecification
	                .hasStatus(
	                        request.getStatus()
	                ));

	    }
	    else{


	        specification =
	                specification.and(
	                AdmissionSpecification
	                .hasStatus(
	                        AdmissionStatus.ACTIVE
	                ));

	    }








	    /*
	     * Step 3:
	     *
	     * Admission Number Search
	     */


	    if(request.getAdmissionNumber() != null
	            &&
	       !request.getAdmissionNumber().isBlank()){


	        specification =
	                specification.and(
	                AdmissionSpecification
	                .hasAdmissionNumber(
	                        request.getAdmissionNumber()
	                ));

	    }








	    /*
	     * Step 4:
	     *
	     * Student Id Filter
	     */


	    if(request.getStudentId() != null){


	        specification =
	                specification.and(
	                AdmissionSpecification
	                .hasStudentId(
	                        request.getStudentId()
	                ));

	    }








	    /*
	     * Step 5:
	     *
	     * Student Name Search
	     */


	    if(request.getStudentName() != null
	            &&
	       !request.getStudentName().isBlank()){


	        specification =
	                specification.and(
	                AdmissionSpecification
	                .hasStudentName(
	                        request.getStudentName()
	                ));

	    }








	    /*
	     * Step 6:
	     *
	     * Department Filter
	     */


	    if(request.getDepartmentId() != null){


	        specification =
	                specification.and(
	                AdmissionSpecification
	                .hasDepartmentId(
	                        request.getDepartmentId()
	                ));

	    }








	    /*
	     * Step 7:
	     *
	     * Course Filter
	     */


	    if(request.getCourseId() != null){


	        specification =
	                specification.and(
	                AdmissionSpecification
	                .hasCourseId(
	                        request.getCourseId()
	                ));

	    }








	    /*
	     * Step 8:
	     *
	     * Academic Year Filter
	     */


	    if(request.getAcademicYear() != null){


	        specification =
	                specification.and(
	                AdmissionSpecification
	                .hasAcademicYear(
	                        request.getAcademicYear()
	                ));

	    }








	    /*
	     * Step 9:
	     *
	     * Semester Filter
	     */


	    if(request.getSemester() != null){


	        specification =
	                specification.and(
	                AdmissionSpecification
	                .hasSemester(
	                        request.getSemester()
	                ));

	    }








	    /*
	     * Step 10:
	     *
	     * Sorting + Pagination
	     */


	    Sort sort =
	            createSort(
	                    sortBy,
	                    direction
	            );




	    Pageable pageable =
	            PageRequest.of(
	                    page,
	                    size,
	                    sort
	            );








	    /*
	     * Step 11:
	     *
	     * Execute Specification query
	     */


	    Page<Admission> admissionPage =
	            admissionRepository
	            .findAll(
	                    specification,
	                    pageable
	            );








	    /*
	     * Step 12:
	     *
	     * Entity -> DTO conversion
	     */


	    List<AdmissionResponseDto> content =
	            admissionPage
	            .getContent()
	            .stream()
	            .map(admissionMapper::toDto)
	            .toList();








	    /*
	     * Step 13:
	     *
	     * Pagination Response
	     */


	    return createPageResponse(
	            admissionPage,
	            content
	    );

	}
	
	
	
	
	// =====================================================
	// SORT VALIDATION
	// =====================================================

	private Sort createSort(
	        String sortBy,
	        String direction) {


	    /*
	     * Allowed sorting fields
	     */
	    List<String> allowedFields =
	            List.of(
	                    "id",
	                    "admissionNumber",
	                    "admissionDate",
	                    "academicYear"
	            );


	    /*
	     * Default sort field
	     */
	    if(sortBy == null ||
	       !allowedFields.contains(sortBy)) {

	        sortBy = "id";
	    }



	    /*
	     * Default direction DESC
	     */
	    if(direction == null ||
	       !direction.equalsIgnoreCase("asc")) {


	        return Sort.by(sortBy)
	                .descending();
	    }



	    return Sort.by(sortBy)
	            .ascending();

	}
	
	
	
	// =====================================================
	// HELPER METHODS
	// =====================================================


	private Student getActiveStudent(Long studentId) {


	    return studentRepository
	            .findByIdAndStatusNot(
	                    studentId,
	                    RecordStatus.DELETED
	            )
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                    "Student not found with id : "
	                    + studentId
	            ));

	}





	private Department getActiveDepartment(Long departmentId) {


	    return departmentRepository
	            .findByIdAndStatus(
	                    departmentId,
	                    RecordStatus.ACTIVE
	            )
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                    "Department not found with id : "
	                    + departmentId
	            ));

	}





	private Course getActiveCourse(Long courseId) {


	    return courseRepository
	            .findByIdAndStatus(
	                    courseId,
	                    RecordStatus.ACTIVE
	            )
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                    "Course not found with id : "
	                    + courseId
	            ));

	}





	private void validateCourseDepartment(
	        Course course,
	        Department department) {


	    if(!course.getDepartment()
	            .getId()
	            .equals(
	            department.getId())) {


	        throw new BusinessException(
	                "Course does not belong to department"
	        );

	    }

	}





	private void validateDuplicateAdmission(
	        Long studentId) {


	    boolean exists =
	            admissionRepository
	            .existsByStudent_IdAndStatus(
	                    studentId,
	                    AdmissionStatus.ACTIVE
	            );


	    if(exists) {


	        throw new BusinessException(
	                "Student already has active admission"
	        );

	    }

	}
	
	
	
	private void validateDuplicateAdmissionForUpdate(
	        Long admissionId,
	        Long studentId
	){
	    

	    boolean exists =
	            admissionRepository
	            .existsByStudent_IdAndStatus(
	                    studentId,
	                    AdmissionStatus.ACTIVE
	            );


	    if(exists){


	        Admission existing =
	                admissionRepository
	                .findById(admissionId)
	                .orElseThrow();


	        if(!existing.getStudent()
	                .getId()
	                .equals(studentId)){


	            throw new BusinessException(
	            "Student already has active admission");

	        }
	    }

	}
	
	
	// =====================================================
	// PAGE RESPONSE HELPER
	// =====================================================

	private <E, D> PageResponse<D> createPageResponse(Page<E> page, List<D> content) {

		PageResponse<D> response = new PageResponse<>();

		response.setContent(content);

		response.setPageNumber(page.getNumber());

		response.setPageSize(page.getSize());

		response.setTotalElements(page.getTotalElements());

		response.setTotalPages(page.getTotalPages());

		response.setLast(page.isLast());

		return response;

	}


}