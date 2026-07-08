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
import com.sms.entity.CourseStatus;
import com.sms.entity.Department;
import com.sms.entity.DepartmentStatus;
import com.sms.entity.Student;
import com.sms.entity.StudentStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.repository.CourseRepository;
import com.sms.repository.DepartmentRepository;
import com.sms.repository.StudentRepository;

@Service
@Transactional
public class AdmissionServiceImpl implements AdmissionService {


    private final AdmissionRepository admissionRepository;

    private final StudentRepository studentRepository;

    private final DepartmentRepository departmentRepository;

    private final CourseRepository courseRepository;

    private final AdmissionMapper admissionMapper;


    public AdmissionServiceImpl(
            AdmissionRepository admissionRepository,
            StudentRepository studentRepository,
            DepartmentRepository departmentRepository,
            CourseRepository courseRepository,
            AdmissionMapper admissionMapper) {


        this.admissionRepository = admissionRepository;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.admissionMapper = admissionMapper;
    }

    @Override
    public AdmissionResponseDto createAdmission(
            AdmissionRequestDto dto) {

        //Student exist hona chahiye aur deleted student ko admission nahi milega.
        Student student = studentRepository
                .findByIdAndStatusNot(
                        dto.getStudentId(),
                        StudentStatus.DELETED
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                        		"Student not found with id: "
                        				+ dto.getStudentId()));

        //Ek student ke paas ek hi ACTIVE admission hona chahiye.
        boolean exists =
                admissionRepository
                .existsByStudent_IdAndStatus(
                        dto.getStudentId(),
                        AdmissionStatus.ACTIVE
                );

        if (exists) {
            throw new BusinessException(
                    "Student already has active admission");
        }

        //Admission create karte waqt selected Department exist hona chahiye aur ACTIVE hona chahiye.
        Department department =
                departmentRepository
                .findByIdAndStatus(
                        dto.getDepartmentId(),
                        DepartmentStatus.ACTIVE
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                        		"Department not found with id: "
                        				+ dto.getDepartmentId()));

        //Course exist hona chahiye aur ACTIVE hona chahiye.
        Course course =
                courseRepository
                .findByIdAndStatus(
                        dto.getCourseId(),
                        CourseStatus.ACTIVE
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                        		"Course not found with id: "
                        				+ dto.getCourseId()));

        //Selected Course selected Department ka hi hona chahiye.
        if (!course.getDepartment()
                .getId()
                .equals(department.getId())) {

            throw new BusinessException(
                    "Course does not belong to department");
        }
        
        //Request DTO ko Admission entity me convert karna.
        Admission admission = admissionMapper.toEntity(dto);

        //Validated Student ko Admission ke saath set karna.
        admission.setStudent(student);

        //Validated Department ko Admission ke saath set karna.
        admission.setDepartment(department);

        //Validated Course ko Admission ke saath set karna.
        admission.setCourse(course);

      //System automatically unique admission number generate karega.
        admission.setAdmissionNumber(
                generateAdmissionNumber()
        );

        //Naya admission create hote hi uska status ACTIVE set hoga.
        admission.setStatus(
                AdmissionStatus.ACTIVE
        );

        //Agar admission date request me nahi di gayi ho,
        //to current date automatically set karna.
        if (admission.getAdmissionDate() == null) {

            admission.setAdmissionDate(
                    java.time.LocalDate.now()
            );
        }

        //Admission ko database me save karna.
        Admission saved =
                admissionRepository.save(admission);

        //Saved Admission ko Response DTO me convert karke return karna.
        return admissionMapper.toDto(saved);

    }	
    
  //Admin manually admission number nahi dalega.
  //System automatically unique admission number generate karega.
  private String generateAdmissionNumber() {

      //Current year lena.
      String year = String.valueOf(
              java.time.Year.now().getValue()
      );

      //UUID se unique 6-character code generate karna.
      String uniqueId = java.util.UUID
              .randomUUID()
              .toString()
              .substring(0, 6)
              .toUpperCase();

      //Format: ADM + Current Year + Unique Code
      //Example: ADM2026A1B2C3
      return "ADM"
              + year
              + uniqueId;
  }
  
  	//---------------- GET BY ID ----------------
	@Override
	public AdmissionResponseDto getAdmissionById(Long id) {

		// Admission find karna.
		// Cancelled admission ko normal fetch me allow nahi karenge.
		Admission admission = 
				admissionRepository
				.findByIdAndStatusNot(
						id, 
						AdmissionStatus.CANCELLED
				)
				.orElseThrow(() -> 
					new ResourceNotFoundException(
							"Admission not found with id: " 
					+ id
				));

		// Entity ko Response DTO me convert karke return karna.
		return admissionMapper.toDto(admission);

	}
	
	
	// ---------------- GET ALL WITH PAGINATION ----------------
	@Override
	public PageResponse<AdmissionResponseDto> getAllAdmissions(
	        int page,
	        int size,
	        String sortBy,
	        String direction) {


	    //Sort create karna
	    Sort sort = direction.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();



		// Pagination object banana
		Pageable pageable = PageRequest.of(page, size, sort);


		// Cancelled admission exclude karna
		Page<Admission> admissionPage = admissionRepository.findByStatusNot(AdmissionStatus.CANCELLED, pageable);


	    //Entity List ko DTO List me convert karna
		List<AdmissionResponseDto> content = 
				admissionPage.getContent().stream().map(admissionMapper::toDto).toList();

	    //Response object banana
		PageResponse<AdmissionResponseDto> response = new PageResponse<>();


		response.setContent(content);
		response.setPageNumber(admissionPage.getNumber());
		response.setPageSize(admissionPage.getSize());
		response.setTotalElements(admissionPage.getTotalElements());
		response.setTotalPages(admissionPage.getTotalPages());
		response.setLast(admissionPage.isLast());

	    return response;
	}

	
	
	
	@Override
	public PageResponse<AdmissionResponseDto> searchAdmissions(
	        AdmissionSearchRequest request,
	        int page,
	        int size,
	        String sortBy,
	        String direction) {


	    Specification<Admission> spec =
	            Specification.where(null);



	    // Default ACTIVE filter
	    if(request.getStatus()!=null){

	        spec = spec.and(
	            AdmissionSpecification
	            .hasStatus(request.getStatus())
	        );

	    }else{

	        spec = spec.and(
	            AdmissionSpecification
	            .hasStatus(
	            AdmissionStatus.ACTIVE)
	        );
	    }



	    if(request.getAdmissionNumber()!=null
	       && !request.getAdmissionNumber().isBlank()){

	        spec = spec.and(
	        AdmissionSpecification
	        .hasAdmissionNumber(
	        request.getAdmissionNumber()));

	    }



	    if(request.getStudentName()!=null
	       && !request.getStudentName().isBlank()){

	        spec = spec.and(
	        AdmissionSpecification
	        .hasStudentName(
	        request.getStudentName()));

	    }



	    if(request.getAcademicYear()!=null){

	        spec = spec.and(
	        AdmissionSpecification
	        .hasAcademicYear(
	        request.getAcademicYear()));

	    }



	    if(request.getSemester()!=null){

	        spec = spec.and(
	        AdmissionSpecification
	        .hasSemester(
	        request.getSemester()));

	    }



	    if(request.getDepartmentId()!=null){

	        spec = spec.and(
	        AdmissionSpecification
	        .hasDepartment(
	        request.getDepartmentId()));

	    }



	    if(request.getCourseId()!=null){

	        spec = spec.and(
	        AdmissionSpecification
	        .hasCourse(
	        request.getCourseId()));

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
	        sort);



	    Page<Admission> admissionPage =
	            admissionRepository
	            .findAll(spec,pageable);



	    List<AdmissionResponseDto> content =
	            admissionPage.getContent()
	            .stream()
	            .map(admissionMapper::toDto)
	            .toList();



	    PageResponse<AdmissionResponseDto> response =
	            new PageResponse<>();


	    response.setContent(content);
	    response.setPageNumber(admissionPage.getNumber());
	    response.setPageSize(admissionPage.getSize());
	    response.setTotalElements(admissionPage.getTotalElements());
	    response.setTotalPages(admissionPage.getTotalPages());
	    response.setLast(admissionPage.isLast());


	    return response;
	}

	// ---------------- UPDATE ----------------
	@Override
	public AdmissionResponseDto updateAdmission(
	        Long id,
	        AdmissionRequestDto dto) {


	    // Existing admission fetch karna - Admission exist hona chahiye aur CANCELLED admission update nahi hoga.
	    Admission admission =
	            admissionRepository
	            .findByIdAndStatusNot(
	                    id,
	                    AdmissionStatus.CANCELLED
	            )
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                    "Admission not found with id: "
	                    + id
	            ));


	    // Student validate karna - Selected Student exist hona chahiye aur DELETED student assign nahi hoga.
	    Student student =
	            studentRepository
	            .findByIdAndStatusNot(
	                    dto.getStudentId(),
	                    StudentStatus.DELETED
	            )
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                    "Student not found with id: "
	                    + dto.getStudentId()
	            ));


	    // Check duplicate ACTIVE admission - Agar student change kiya gaya hai 
	    //to uske paas pehle se ACTIVE admission nahi hona chahiye.
	    boolean exists =
	            admissionRepository
	            .existsByStudent_IdAndStatus(
	                    dto.getStudentId(),
	                    AdmissionStatus.ACTIVE
	            );


		if (exists && !admission.getStudent().getId().equals(dto.getStudentId())) {

			throw new BusinessException
				("Student already has active admission");
		}


	    // Department validate - Selected Department exist hona chahiye aur ACTIVE hona chahiye.
	    Department department =
	            departmentRepository
	            .findByIdAndStatus(
	                    dto.getDepartmentId(),
	                    DepartmentStatus.ACTIVE
	            )
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                    "Department not found with id: "
	                    + dto.getDepartmentId()
	            ));



	    // Course validate - Selected Course exist hona chahiye aur ACTIVE hona chahiye.
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
	            ));



	    // Course belongs to Department check - Selected Course selected Department ka hi hona chahiye.
		if (!course.getDepartment().getId().equals(department.getId())) {

	        throw new BusinessException(
	        		"Course does not belong to department");
	    }


		LocalDate oldDate = admission.getAdmissionDate();
		// Request DTO ki updated values entity me copy karna.
		admissionMapper.updateEntity(admission, dto);
		
		if(dto.getAdmissionDate() == null){
		    admission.setAdmissionDate(oldDate);
		}
		

		//Validated Student ko Admission ke saath set karna.
	    admission.setStudent(student);

	    //Validated Department ko Admission ke saath set karna.
	    admission.setDepartment(department);

	    //Validated Course ko Admission ke saath set karna.
	    admission.setCourse(course);

	    //Updated Admission ko database me save karna.
	    Admission updated =
	            admissionRepository.save(admission);

	    //Updated Admission ko Response DTO me convert karke return karna.
	    return admissionMapper.toDto(updated);

	}

	// ---------------- DELETE / CANCEL ADMISSION ----------------
	@Override
	public void deleteAdmission(Long id) {

		//Admission exist hona chahiye.
	    Admission admission =
	            admissionRepository
	            .findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                    "Admission not found with id: "
	                    + id
	            ));

	    //Already cancelled admission dobara cancel nahi hoga.
	    if(admission.getStatus()
	            == AdmissionStatus.CANCELLED){

	        throw new BusinessException(
	                "Admission already cancelled"
	        );
	    }

	    //Completed admission cancel nahi ho sakta.
	    if(admission.getStatus()
	            == AdmissionStatus.COMPLETED){

	        throw new BusinessException(
	                "Completed admission cannot be cancelled"
	        );
	    }

	    //Active admission ko CANCELLED status me convert karna.
	    admission.setStatus(
	            AdmissionStatus.CANCELLED
	    );


	    admissionRepository.save(admission);

	}
	
	
	
}