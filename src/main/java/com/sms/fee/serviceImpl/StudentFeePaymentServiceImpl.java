package com.sms.fee.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admission.entity.Admission;
import com.admission.entity.AdmissionStatus;
import com.admission.repository.AdmissionRepository;
import com.sms.dto.PageResponse;
import com.sms.entity.Student;
import com.sms.enums.RecordStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.fee.dto.StudentFeePaymentRequestDto;
import com.sms.fee.dto.StudentFeePaymentResponseDto;
import com.sms.fee.dto.StudentFeePaymentSearchRequest;
import com.sms.fee.entity.StudentFeePayment;
import com.sms.fee.enums.PaymentStatus;
import com.sms.fee.mapper.StudentFeePaymentMapper;
import com.sms.fee.repository.FeeStructureRepository;
import com.sms.fee.repository.StudentFeePaymentRepository;
import com.sms.fee.service.StudentFeePaymentService;
import com.sms.fee.specification.StudentFeePaymentSpecification;
import com.sms.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentFeePaymentServiceImpl 
        implements StudentFeePaymentService {


    private final StudentFeePaymentRepository studentFeePaymentRepository;

    private final StudentRepository studentRepository;

    private final AdmissionRepository admissionRepository;

    private final FeeStructureRepository feeStructureRepository;

    private final StudentFeePaymentMapper mapper;

	
    @Override
    public StudentFeePaymentResponseDto createPayment(
            StudentFeePaymentRequestDto dto) {


        // =====================================================
        // 1. Validate Student
        // =====================================================


        Student student =
                studentRepository
                .findByIdAndStatusNot(
                        dto.getStudentId(),
                        RecordStatus.DELETED
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found"
                        )
                );
        
        

     

     

        // =====================================================
        // 2. Validate Admission
        // =====================================================


        Admission admission =
                admissionRepository
                .findByIdAndAdmissionStatus(
                        dto.getAdmissionId(),
                        AdmissionStatus.ACTIVE
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                            "Active admission not found" 
                        )
                );


        // =====================================================
        // 3. Duplicate Payment Check
        // =====================================================

        boolean exists =
                studentFeePaymentRepository
                .existsByStudentIdAndAdmissionIdAndAcademicYearAndStatusNot(
                        dto.getStudentId(),
                        dto.getAdmissionId(),
                        dto.getAcademicYear(),
                        RecordStatus.DELETED
                );


        if(exists){

            throw new BusinessException(
                    "Fee payment already exists for this academic year"
            );
        }


        // =====================================================
        // 4. Calculate Total Fee
        // =====================================================


        BigDecimal totalFee =
                feeStructureRepository
                .findTotalFeeByCourseAndSemester(
                        admission.getCourse().getId(),
                        admission.getSemester().getId(),
                        dto.getAcademicYear()
                );



        if(totalFee == null){

            throw new BusinessException(
                "Fee structure not configured"
            );
        }




        // =====================================================
        // 5. Validate Paid Amount
        // =====================================================


        if(dto.getPaidAmount() == null){

            throw new BusinessException(
                    "Paid amount is required"
            );
        }

        if(dto.getPaidAmount()
                .compareTo(totalFee) > 0){

            throw new BusinessException(
                    "Paid amount cannot be greater than total fee"
            );
        }





        // =====================================================
        // 6. Calculate Due
        // =====================================================


        BigDecimal dueAmount =
                totalFee.subtract(
                        dto.getPaidAmount()
                );





        // =====================================================
        // 7. Payment Status
        // =====================================================


        PaymentStatus paymentStatus =
                calculatePaymentStatus(
                        dto.getPaidAmount(),
                        dueAmount
                );





        // =====================================================
        // 8. DTO -> Entity
        // =====================================================


        StudentFeePayment payment =
                mapper.toEntity(dto);



        payment.setStudent(student);

        payment.setAdmission(admission);

        payment.setTotalFee(totalFee);

        payment.setDueAmount(dueAmount);

        payment.setPaymentStatus(paymentStatus);

        payment.setStatus(
                RecordStatus.ACTIVE
        );




        // =====================================================
        // 9. Save
        // =====================================================


        StudentFeePayment saved =
        		studentFeePaymentRepository.save(payment);



        return mapper.toDto(saved);

    }
    
    

 // =====================================================
    // GET BY ID
    // =====================================================


    @Override
    @Transactional(readOnly = true)
    public StudentFeePaymentResponseDto getPaymentById(
            Long id) {


        StudentFeePayment payment =
        		studentFeePaymentRepository
                .findByIdAndStatusNot(
                        id,
                        RecordStatus.DELETED
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Fee payment not found with id : "
                                + id
                        )
                );


        return mapper.toDto(payment);
    }





 // =====================================================
 // UPDATE PAYMENT
 // =====================================================

 @Override
 public StudentFeePaymentResponseDto updatePayment(
         Long id,
         StudentFeePaymentRequestDto dto) {


     // =====================================================
     // Validate Existing Payment
     // =====================================================

     StudentFeePayment payment =
             studentFeePaymentRepository
             .findByIdAndStatusNot(
                     id,
                     RecordStatus.DELETED
             )
             .orElseThrow(() ->
                     new ResourceNotFoundException(
                             "Fee payment not found"
                     )
             );


     // =====================================================
     // Duplicate Payment Validation
     // =====================================================

     boolean exists =
             studentFeePaymentRepository
             .existsByStudentIdAndAdmissionIdAndAcademicYearAndStatusNotAndIdNot(
                     dto.getStudentId(),
                     dto.getAdmissionId(),
                     dto.getAcademicYear(),
                     RecordStatus.DELETED,
                     id
             );

     if (exists) {

         throw new BusinessException(
                 "Fee payment already exists for this academic year"
         );
     }


     // =====================================================
     // Validate Student
     // =====================================================

     Student student =
             studentRepository
             .findByIdAndStatusNot(
                     dto.getStudentId(),
                     RecordStatus.DELETED
             )
             .orElseThrow(() ->
                     new ResourceNotFoundException(
                             "Student not found"
                     )
             );


     // =====================================================
     // Validate Admission
     // =====================================================

     Admission admission =
             admissionRepository
             .findByIdAndAdmissionStatus(
                     dto.getAdmissionId(),
                     AdmissionStatus.ACTIVE
             )
             .orElseThrow(() ->
                     new ResourceNotFoundException(
                             "Active admission not found"
                     )
             );


     // =====================================================
     // Calculate Total Fee
     // =====================================================

     BigDecimal totalFee =
             feeStructureRepository
             .findTotalFeeByCourseAndSemester(
                     admission.getCourse().getId(),
                     admission.getSemester().getId(),
                     dto.getAcademicYear()
             );


     if (totalFee == null
             || totalFee.compareTo(BigDecimal.ZERO) <= 0) {

         throw new BusinessException(
                 "Fee structure not configured"
         );
     }


     // =====================================================
     // Validate Paid Amount
     // =====================================================

     if (dto.getPaidAmount() == null) {

         throw new BusinessException(
                 "Paid amount is required"
         );
     }

     if (dto.getPaidAmount().compareTo(BigDecimal.ZERO) < 0) {

         throw new BusinessException(
                 "Paid amount cannot be negative"
         );
     }

     if (dto.getPaidAmount().compareTo(totalFee) > 0) {

         throw new BusinessException(
                 "Paid amount cannot be greater than total fee"
         );
     }


     // =====================================================
     // Calculate Due Amount
     // =====================================================

     BigDecimal dueAmount =
             totalFee.subtract(
                     dto.getPaidAmount()
             );


     // =====================================================
     // Calculate Payment Status
     // =====================================================

     PaymentStatus paymentStatus =
             calculatePaymentStatus(
                     dto.getPaidAmount(),
                     dueAmount
             );


     // =====================================================
     // Update Entity
     // =====================================================

     mapper.updateEntity(
             payment,
             dto
     );

     payment.setStudent(student);
     payment.setAdmission(admission);
     payment.setTotalFee(totalFee);
     payment.setDueAmount(dueAmount);
     payment.setPaymentStatus(paymentStatus);
     payment.setStatus(RecordStatus.ACTIVE);


     // =====================================================
     // Save
     // =====================================================

     StudentFeePayment updated =
             studentFeePaymentRepository.save(payment);


     return mapper.toDto(updated);

 }






    // =====================================================
    // DELETE (SOFT DELETE)
    // =====================================================


    @Override
    public void deletePayment(
            Long id) {



        StudentFeePayment payment =
        		studentFeePaymentRepository
                .findByIdAndStatusNot(
                        id,
                        RecordStatus.DELETED
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Fee payment not found"
                        )
                );



        payment.setStatus(
                RecordStatus.DELETED
        );


        studentFeePaymentRepository.save(payment);

    }







    // =====================================================
    // GET ALL PAGINATION
    // =====================================================


    @Override
    @Transactional(readOnly = true)
    public PageResponse<StudentFeePaymentResponseDto> 
    getAllPayments(
            int page,
            int size,
            String sortBy,
            String direction) {



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



        Page<StudentFeePayment> result =
        		studentFeePaymentRepository
                .findByStatusNot(
                        RecordStatus.DELETED,
                        pageable
                );



        List<StudentFeePaymentResponseDto> content =
                result.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();



        PageResponse<StudentFeePaymentResponseDto> response =
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







    // =====================================================
    // SEARCH
    // =====================================================


    @Override
    @Transactional(readOnly = true)
    public PageResponse<StudentFeePaymentResponseDto>
    searchPayments(
            StudentFeePaymentSearchRequest request,
            int page,
            int size,
            String sortBy,
            String direction) {



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



        Specification<StudentFeePayment> specification =
                StudentFeePaymentSpecification
                .getSpecification(
                        request.getStudentId(),
                        request.getAdmissionId(),
                        request.getAcademicYear(),
                        request.getPaymentStatus(),
                        request.getPaymentMode(),
                        request.getMinPaidAmount(),
                        request.getMaxPaidAmount(),
                        request.getStatus()
                );



        Page<StudentFeePayment> result =
        		studentFeePaymentRepository.findAll(
                        specification,
                        pageable
                );



        List<StudentFeePaymentResponseDto> content =
                result.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();



        PageResponse<StudentFeePaymentResponseDto> response =
                new PageResponse<>();


        response.setContent(content);

        response.setPageNumber(result.getNumber());

        response.setPageSize(result.getSize());

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




	 // =====================================================
	 // PAYMENT STATUS CALCULATION
	 // =====================================================
	
    private PaymentStatus calculatePaymentStatus(
            BigDecimal paidAmount,
            BigDecimal dueAmount) {

        if(paidAmount == null 
                || paidAmount.compareTo(BigDecimal.ZERO) <= 0){

            return PaymentStatus.PENDING;
        }

        if(dueAmount == null
                || dueAmount.compareTo(BigDecimal.ZERO) == 0){

            return PaymentStatus.PAID;
        }

        return PaymentStatus.PARTIAL;
    }

}
