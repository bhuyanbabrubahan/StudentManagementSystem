package com.sms.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.dto.FacultySubjectRequestDto;
import com.sms.dto.FacultySubjectResponseDto;
import com.sms.dto.FacultySubjectSearchRequest;
import com.sms.dto.PageResponse;
import com.sms.entity.Faculty;
import com.sms.entity.FacultySubjectMapping;
import com.sms.entity.Subject;
import com.sms.enums.RecordStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.mapper.FacultySubjectMapper;
import com.sms.repository.FacultyRepository;
import com.sms.repository.FacultySubjectRepository;
import com.sms.repository.SubjectRepository;
import com.sms.service.FacultySubjectService;
import com.sms.specification.FacultySubjectSpecification;

@Service
@Transactional
public class FacultySubjectServiceImpl implements FacultySubjectService {

	private final FacultySubjectRepository repository;

	private final FacultyRepository facultyRepository;

	private final SubjectRepository subjectRepository;

	private final FacultySubjectMapper mapper;

	public FacultySubjectServiceImpl(FacultySubjectRepository repository, FacultyRepository facultyRepository,
			SubjectRepository subjectRepository, FacultySubjectMapper mapper) {

		this.repository = repository;

		this.facultyRepository = facultyRepository;

		this.subjectRepository = subjectRepository;

		this.mapper = mapper;

	}

	@Override
	public FacultySubjectResponseDto assignSubject(FacultySubjectRequestDto dto) {

		// 1. Duplicate check

		if (repository.existsByFacultyIdAndSubjectIdAndAcademicYearAndStatusNot(
		        dto.getFacultyId(),
		        dto.getSubjectId(),
		        dto.getAcademicYear(),
		        RecordStatus.DELETED)) {

			throw new BusinessException("Subject already assigned to faculty for this academic year.");
		}

		// 2. Find Faculty

		Faculty faculty = facultyRepository.findByIdAndStatus(dto.getFacultyId(), RecordStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

		// 3. Find Subject

		Subject subject = subjectRepository.findByIdAndStatusNot(dto.getSubjectId(), RecordStatus.DELETED)
				.orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

		// 4. DTO -> Entity

		FacultySubjectMapping mapping = mapper.toEntity(dto);

		// 5. Set Relationship

		mapping.setFaculty(faculty);

		mapping.setSubject(subject);

		// 6. Default Status

		mapping.setStatus(RecordStatus.ACTIVE);

		// 7. Save

		FacultySubjectMapping saved = repository.save(mapping);

		// 8. Response

		return mapper.toDto(saved);

	}

	@Override
	@Transactional(readOnly = true)
	public FacultySubjectResponseDto getById(Long id) {

		FacultySubjectMapping mapping = repository.findByIdAndStatusNot(
				id, RecordStatus.DELETED)
				.orElseThrow(() -> new ResourceNotFoundException("Faculty subject mapping not found"));

		return mapper.toDto(mapping);

	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<FacultySubjectResponseDto> getAll(int page, int size, String sortBy, String direction) {

		Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<FacultySubjectMapping> result =
		        repository.findByStatusNot(
		        		RecordStatus.DELETED,
		                pageable);

		List<FacultySubjectResponseDto> content = result.getContent().stream().map(mapper::toDto).toList();

		PageResponse<FacultySubjectResponseDto> response = new PageResponse<>();

		response.setContent(content);

		response.setPageNumber(result.getNumber());

		response.setPageSize(result.getSize());

		response.setTotalElements(result.getTotalElements());

		response.setTotalPages(result.getTotalPages());

		response.setLast(result.isLast());

		return response;

	}

	
	
	@Override
	public FacultySubjectResponseDto update(
	        Long id,
	        FacultySubjectRequestDto dto) {

	    // ==========================
	    // 1. Fetch Existing Mapping
	    // ==========================

	    FacultySubjectMapping mapping = repository
	            .findByIdAndStatusNot(
	                    id,
	                    RecordStatus.DELETED)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Faculty Subject Mapping not found with id : " + id));

	    // ==========================
	    // 2. Duplicate Validation
	    // ==========================

	    if (repository.existsByFacultyIdAndSubjectIdAndAcademicYearAndStatusNotAndIdNot(
	            dto.getFacultyId(),
	            dto.getSubjectId(),
	            dto.getAcademicYear(),
	            RecordStatus.DELETED,
	            id)) {

	        throw new BusinessException(
	                "Faculty is already assigned to this subject for academic year : "
	                        + dto.getAcademicYear());
	    }

	    // ==========================
	    // 3. Fetch Active Faculty
	    // ==========================

	    Faculty faculty = facultyRepository
	            .findByIdAndStatus(
	                    dto.getFacultyId(),
	                    RecordStatus.ACTIVE)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Faculty not found with id : " + dto.getFacultyId()));

	    // ==========================
	    // 4. Fetch Active Subject
	    // ==========================

	    Subject subject = subjectRepository
	            .findByIdAndStatusNot(
	                    dto.getSubjectId(),
	                    RecordStatus.DELETED)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Subject not found with id : " + dto.getSubjectId()));

	    // ==========================
	    // 5. Update Relationship
	    // ==========================

	    mapping.setFaculty(faculty);

	    mapping.setSubject(subject);

	    // ==========================
	    // 6. Update Business Fields
	    // ==========================

	    mapping.setAssignedDate(dto.getAssignedDate());

	    mapping.setAcademicYear(dto.getAcademicYear());

	    // Status intentionally not updated.
	    // Soft delete has a dedicated API.

	    // ==========================
	    // 7. Save
	    // ==========================

	    FacultySubjectMapping updated = repository.save(mapping);

	    // ==========================
	    // 8. Return Response
	    // ==========================

	    return mapper.toDto(updated);
	}
	
	
	@Override
	public void delete(Long id) {

	    FacultySubjectMapping mapping =
	            repository.findByIdAndStatusNot(
	                    id,
	                    RecordStatus.DELETED)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Faculty Subject Mapping not found with id : " + id));

	    mapping.setStatus(
	    		RecordStatus.DELETED);

	    repository.save(mapping);
	}
	
	
	
	
	@Override
	@Transactional(readOnly = true)
	public PageResponse<FacultySubjectResponseDto> search(
	        FacultySubjectSearchRequest request,
	        int page,
	        int size,
	        String sortBy,
	        String direction) {

	    Specification<FacultySubjectMapping> spec = Specification.where(null);

	    // ==========================
	    // STATUS
	    // ==========================

	    if (request.getStatus() != null) {

	        spec = spec.and(
	                FacultySubjectSpecification.hasStatus(
	                        request.getStatus()));

	    } else {

	        spec = spec.and(
	                FacultySubjectSpecification.statusNot(
	                		RecordStatus.DELETED));

	    }

	    // ==========================
	    // FACULTY
	    // ==========================

	    if (request.getFacultyId() != null) {

	        spec = spec.and(
	                FacultySubjectSpecification.hasFaculty(
	                        request.getFacultyId()));
	    }

	    // ==========================
	    // SUBJECT
	    // ==========================

	    if (request.getSubjectId() != null) {

	        spec = spec.and(
	                FacultySubjectSpecification.hasSubject(
	                        request.getSubjectId()));
	    }

	    // ==========================
	    // ACADEMIC YEAR
	    // ==========================

	    if (request.getAcademicYear() != null
	            && !request.getAcademicYear().isBlank()) {

	        spec = spec.and(
	                FacultySubjectSpecification.hasAcademicYear(
	                        request.getAcademicYear()));
	    }

	    // ==========================
	    // SORTING
	    // ==========================

	    Sort sort = direction.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    // ==========================
	    // SEARCH
	    // ==========================

	    Page<FacultySubjectMapping> result =
	            repository.findAll(spec, pageable);

	    List<FacultySubjectResponseDto> content =
	            result.getContent()
	                    .stream()
	                    .map(mapper::toDto)
	                    .toList();

	    PageResponse<FacultySubjectResponseDto> response =
	            new PageResponse<>();

	    response.setContent(content);
	    response.setPageNumber(result.getNumber());
	    response.setPageSize(result.getSize());
	    response.setTotalElements(result.getTotalElements());
	    response.setTotalPages(result.getTotalPages());
	    response.setLast(result.isLast());

	    return response;
	}

	

}