package com.sms.fee.refund.serviceImpl;


import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.dto.PageResponse;
import com.sms.enums.RecordStatus;
import com.sms.exception.BusinessException;
import com.sms.fee.entity.StudentFeePayment;
import com.sms.fee.enums.PaymentStatus;
import com.sms.fee.refund.dto.StudentFeeRefundRequestDto;
import com.sms.fee.refund.dto.StudentFeeRefundResponseDto;
import com.sms.fee.refund.dto.StudentFeeRefundSearchRequest;
import com.sms.fee.refund.entity.StudentFeeRefund;
import com.sms.fee.refund.enums.RefundStatus;
import com.sms.fee.refund.mapper.StudentFeeRefundMapper;
import com.sms.fee.refund.repository.StudentFeeRefundRepository;
import com.sms.fee.refund.service.StudentFeeRefundService;
import com.sms.fee.refund.specification.StudentFeeRefundSpecification;
import com.sms.fee.repository.StudentFeePaymentRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
@Transactional
public class StudentFeeRefundServiceImpl
        implements StudentFeeRefundService {


    private final StudentFeeRefundRepository refundRepository;

    private final StudentFeePaymentRepository paymentRepository;

    private final StudentFeeRefundMapper mapper;

    @Override
    public StudentFeeRefundResponseDto createRefund(
            StudentFeeRefundRequestDto request
    ) {


        // =====================================================
        // Find Payment
        // =====================================================


        StudentFeePayment payment =
                paymentRepository
                .findByIdAndStatusNot(
                        request.getPaymentId(),
                        RecordStatus.DELETED
                )
                .orElseThrow(
                        () -> new BusinessException(
                                "Fee payment not found"
                        )
                );




        // =====================================================
        // Validate Payment Status
        // =====================================================


        if(payment.getPaymentStatus()
                == PaymentStatus.CANCELLED
                ||
           payment.getPaymentStatus()
                == PaymentStatus.PENDING){


            throw new BusinessException(
                    "Refund allowed only for paid payment"
            );

        }




        // =====================================================
        // Duplicate Refund Check
        // =====================================================


        boolean exists =
                refundRepository
                .existsByPaymentIdAndStatusNot(
                        payment.getId(),
                        RecordStatus.DELETED
                );


        if(exists){

            throw new BusinessException(
                    "Refund already generated for this payment"
            );

        }





        // =====================================================
        // Refund Amount Validation
        // =====================================================


        if(request.getRefundAmount()
                .compareTo(
                        payment.getPaidAmount()
                ) > 0){


            throw new BusinessException(
                    "Refund amount cannot exceed paid amount"
            );

        }





        // =====================================================
        // DTO To Entity
        // =====================================================


        StudentFeeRefund refund =
                mapper.toEntity(
                        request
                );





        // =====================================================
        // Payment Mapping
        // =====================================================


        refund.setPayment(
                payment
        );





        // =====================================================
        // Refund Number Generation
        // =====================================================


        refund.setRefundNumber(
                "SMS-REF-"
                + LocalDate.now()
                .toString()
                .replace("-", "")
                + "-"
                + UUID.randomUUID()
                .toString()
                .substring(0,6)
                .toUpperCase()
        );





        // =====================================================
        // Refund Date
        // =====================================================


        refund.setRefundDate(
                LocalDate.now()
        );





        // =====================================================
        // Refund Status
        // =====================================================


        refund.setRefundStatus(
                RefundStatus.REQUESTED
        );





	     // =====================================================
	     // Student Snapshot
	     // =====================================================
	
	     refund.setStudentId(
	             payment.getStudent().getId()
	     );
	
	
	     refund.setStudentName(
	             payment.getStudent().getFullName()
	     );
	
	
	     refund.setRollNumber(
	             payment.getStudent().getRollNumber()
	     );
	
	
	
	     // =====================================================
	     // Admission Snapshot
	     // =====================================================
	
	     refund.setAdmissionId(
	             payment.getAdmission().getId()
	     );
	
	
	     refund.setAdmissionNumber(
	             payment.getAdmission().getAdmissionNumber()
	     );





        // =====================================================
        // Default Status
        // =====================================================


        refund.setStatus(
                RecordStatus.ACTIVE
        );





        StudentFeeRefund saved =
                refundRepository.save(
                        refund
                );




        return mapper.toDto(
                saved
        );

    }


    
    
    
    @Override
    @Transactional(readOnly = true)
    public StudentFeeRefundResponseDto getRefundById(
            Long id
    ) {


        StudentFeeRefund refund =
                refundRepository
                .findByIdAndStatusNot(
                        id,
                        RecordStatus.DELETED
                )
                .orElseThrow(
                        () -> new BusinessException(
                                "Refund not found"
                        )
                );


        return mapper.toDto(
                refund
        );

    }
    
    
    


    @Override
    public void deleteRefund(
            Long id
    ) {


        StudentFeeRefund refund =
                refundRepository
                .findByIdAndStatusNot(
                        id,
                        RecordStatus.DELETED
                )
                .orElseThrow(
                        () -> new BusinessException(
                                "Refund not found"
                        )
                );



        refund.setStatus(
                RecordStatus.DELETED
        );


        refundRepository.save(
                refund
        );

    }
    
    
    

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StudentFeeRefundResponseDto> getAllRefunds(
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



        Page<StudentFeeRefund> result =
                refundRepository
                .findByStatusNot(
                        RecordStatus.DELETED,
                        pageable
                );



        Page<StudentFeeRefundResponseDto> dtoPage =
                result.map(mapper::toDto);

        return new PageResponse<>(
                dtoPage.getContent(),
                dtoPage.getNumber(),
                dtoPage.getSize(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages(),
                dtoPage.isLast()
        );

    }
    
    
    
    
    
    
    
    

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StudentFeeRefundResponseDto> searchRefunds(
            StudentFeeRefundSearchRequest request,
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

	     // =====================================================
	     // Validate Date Range
	     // From date should not be greater than To date
	     // =====================================================
	
	     if (request.getFromDate() != null
	             && request.getToDate() != null
	             && request.getFromDate().isAfter(request.getToDate())) {
	
	         throw new BusinessException(
	                 "From date cannot be greater than To date"
	         );
	     }

        Page<StudentFeeRefund> result =
                refundRepository
                .findAll(
                        StudentFeeRefundSpecification.search(request),
                        pageable
                );



        Page<StudentFeeRefundResponseDto> dtoPage =
                result.map(mapper::toDto);

        return new PageResponse<>(
                dtoPage.getContent(),
                dtoPage.getNumber(),
                dtoPage.getSize(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages(),
                dtoPage.isLast()
        );


    }



}