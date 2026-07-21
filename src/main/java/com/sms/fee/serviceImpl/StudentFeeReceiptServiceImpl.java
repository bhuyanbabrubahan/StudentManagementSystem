package com.sms.fee.serviceImpl;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.dto.PageResponse;
import com.sms.enums.RecordStatus;
import com.sms.exception.BusinessException;
import com.sms.exception.ResourceNotFoundException;
import com.sms.fee.dto.StudentFeeReceiptRequestDto;
import com.sms.fee.dto.StudentFeeReceiptResponseDto;
import com.sms.fee.dto.StudentFeeReceiptSearchRequest;
import com.sms.fee.entity.StudentFeePayment;
import com.sms.fee.entity.StudentFeeReceipt;
import com.sms.fee.enums.PaymentStatus;
import com.sms.fee.mapper.StudentFeeReceiptMapper;
import com.sms.fee.repository.StudentFeePaymentRepository;
import com.sms.fee.repository.StudentFeeReceiptRepository;
import com.sms.fee.service.ReceiptPdfService;
import com.sms.fee.service.StudentFeeReceiptService;
import com.sms.fee.specification.StudentFeeReceiptSpecification;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
@Transactional
public class StudentFeeReceiptServiceImpl
        implements StudentFeeReceiptService {




    private final StudentFeeReceiptRepository receiptRepository;

    private final StudentFeePaymentRepository paymentRepository;

    private final StudentFeeReceiptMapper mapper;
    
    private final ReceiptPdfService receiptPdfService;





    // =====================================================
    // Generate Receipt
    // =====================================================



    @Override
    public StudentFeeReceiptResponseDto generateReceipt(
            Long paymentId,
            StudentFeeReceiptRequestDto dto) {



        // =====================================================
        // 1. Validate Payment
        // =====================================================


        StudentFeePayment payment =
                paymentRepository
                .findByIdAndStatusNot(
                        paymentId,
                        RecordStatus.DELETED
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Fee payment not found"
                        )
                );







        // =====================================================
        // 2. Duplicate Receipt Check
        // =====================================================


        boolean exists =
                receiptRepository
                .existsByPaymentIdAndStatusNot(
                        paymentId,
                        RecordStatus.DELETED
                );



        if(exists) {


            throw new BusinessException(
                    "Receipt already generated for this payment"
            );

        }








        // =====================================================
        // 3. Payment Status Validation
        // =====================================================



        if(payment.getPaymentStatus() == PaymentStatus.PENDING
                ||
           payment.getPaymentStatus() == PaymentStatus.CANCELLED){


            throw new BusinessException(
                    "Receipt cannot be generated for pending or cancelled payment"
            );

        }






        // =====================================================
        // 4. DTO -> Entity
        // =====================================================


        StudentFeeReceipt receipt =
                mapper.toEntity(dto);






        // =====================================================
        // 5. Set Payment
        // =====================================================


        receipt.setPayment(
                payment
        );


       // =====================================================
       // 6. Populate Receipt Snapshot
       // =====================================================

       populateSnapshotData(
	            receipt,
	            payment
       );



        // =====================================================
        // 7. Generate Receipt Number
        // =====================================================


        receipt.setReceiptNumber(
                generateReceiptNumber()
        );







        // =====================================================
        // 8. Default Date
        // =====================================================


        if(receipt.getReceiptDate()
                == null) {


            receipt.setReceiptDate(
                    LocalDate.now()
            );

        }







        // =====================================================
        // 9. Status
        // =====================================================


        receipt.setStatus(
                RecordStatus.ACTIVE
        );


        // =====================================================
        // 10. Validate Snapshot
        // =====================================================


        validateReceiptSnapshot(receipt);


        // =====================================================
        // 11. Save
        // =====================================================


        StudentFeeReceipt saved =
                receiptRepository.save(
                        receipt
                );







        // =====================================================
        // 12. Response
        // =====================================================


        return mapper.toDto(saved);

    }
    
    
		 // =====================================================
		 // GET RECEIPT BY ID
		 // =====================================================
		
		
		 @Override
		 @Transactional(readOnly = true)
		 public StudentFeeReceiptResponseDto getReceiptById(
		         Long id) {
		
		
		
		     StudentFeeReceipt receipt =
		             receiptRepository
		             .findByIdAndStatusNot(
		                     id,
		                     RecordStatus.DELETED
		             )
		             .orElseThrow(() ->
		                     new ResourceNotFoundException(
		                             "Fee receipt not found with id : "
		                             + id
		                     )
		             );
		
		
		
		     return mapper.toDto(receipt);
		
		 }
		
		
		
		
		
		// =====================================================
		// DELETE RECEIPT (SOFT DELETE)
		// =====================================================

		@Override
		@Transactional
		public void deleteReceipt(
		        Long id
		) {


		    StudentFeeReceipt receipt =
		            receiptRepository
		            .findByIdAndStatusNot(
		                    id,
		                    RecordStatus.DELETED
		            )
		            .orElseThrow(() ->
		                    new ResourceNotFoundException(
		                            "Fee receipt not found with id : "
		                            + id
		                    )
		            );



		    // ==============================
		    // Business Validation
		    // ==============================

		    if(receipt.getPayment() != null
		            &&
		       receipt.getPayment()
		              .getPaymentStatus()
		              == PaymentStatus.PAID) {


		        throw new BusinessException(
		                "Paid payment receipt cannot be deleted"
		        );

		    }



		    // ==============================
		    // Soft Delete
		    // ==============================

		    receipt.setStatus(
		            RecordStatus.DELETED
		    );


		    receiptRepository.save(
		            receipt
		    );

		}
		
		
		
		
		
		
		
		 // =====================================================
		 // GET ALL RECEIPTS PAGINATION
		 // =====================================================
		
		
		 @Override
		 @Transactional(readOnly = true)
		 public PageResponse<StudentFeeReceiptResponseDto> getAllReceipts(
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
		
		
		
		
		     Page<StudentFeeReceipt> result =
		             receiptRepository
		             .findByStatusNot(
		                     RecordStatus.DELETED,
		                     pageable
		             );
		
		
		
		
		     List<StudentFeeReceiptResponseDto> content =
		             result.getContent()
		             .stream()
		             .map(mapper::toDto)
		             .toList();
		
		
		
		
		     PageResponse<StudentFeeReceiptResponseDto> response =
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
		 // SEARCH RECEIPTS
		 // =====================================================
		
		
		 @Override
		 @Transactional(readOnly = true)
		 public PageResponse<StudentFeeReceiptResponseDto> searchReceipts(
		         StudentFeeReceiptSearchRequest request,
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
		
		
		
		
		
		
		     Specification<StudentFeeReceipt> specification =
		             StudentFeeReceiptSpecification
		             .getSpecification(
		
		            		 request.getReceiptNumber(),

		                     request.getPaymentId(),

		                     request.getStudentId(),
		                     request.getStudentName(),
		                     request.getRollNumber(),

		                     request.getAdmissionId(),
		                     request.getAdmissionNumber(),
		                     request.getAcademicYear(),

		                     request.getCourseName(),
		                     request.getDepartmentName(),
		                     request.getSemesterName(),

		                     request.getScholarshipId(),

		                     request.getPaymentStatus(),
		                     request.getPaymentMode(),

		                     request.getMinPaidAmount(),
		                     request.getMaxPaidAmount(),

		                     request.getMinTotalFee(),
		                     request.getMaxTotalFee(),

		                     request.getMinDueAmount(),
		                     request.getMaxDueAmount(),

		                     request.getMinFinalPayableAmount(),
		                     request.getMaxFinalPayableAmount(),

		                     request.getReceiptDateFrom(),
		                     request.getReceiptDateTo(),

		                     request.getStatus()
		
		             );
		
		
		
		
		
		
		     Page<StudentFeeReceipt> result =
		             receiptRepository
		             .findAll(
		                     specification,
		                     pageable
		             );
		
		
		
		
		
		
		     List<StudentFeeReceiptResponseDto> content =
		             result.getContent()
		             .stream()
		             .map(mapper::toDto)
		             .toList();
		
		
		
		
		
		
		     PageResponse<StudentFeeReceiptResponseDto> response =
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
		// Receipt Number Generator
		// =====================================================


		private String generateReceiptNumber() {


		    try {


		        StudentFeeReceipt lastReceipt =
		                receiptRepository
		                        .findTopByOrderByIdDesc()
		                        .orElse(null);



		        long nextNumber = 1;



		        if(lastReceipt != null
		                && lastReceipt.getId() != null){


		            nextNumber =
		                    lastReceipt.getId() + 1;

		        }



		        return String.format(

		                "SMS-REC-%s-%06d",

		                LocalDate.now()
		                        .format(
		                          DateTimeFormatter.BASIC_ISO_DATE
		                        ),

		                nextNumber
		        );



		    }
		    catch(Exception e){


		        throw new BusinessException(
		                "Unable to generate fee receipt number"
		        );

		    }

		}
		 
		 
		 
		 
		// =====================================================
		// Populate Receipt Snapshot
		// =====================================================


		private void populateSnapshotData(
		        StudentFeeReceipt receipt,
		        StudentFeePayment payment
		){

		    
		    // ============================
		    // Student Snapshot
		    // ============================

		    if(payment.getStudent() != null){

		        receipt.setStudentId(
		                payment.getStudent().getId()
		        );


		        String firstName =
		                payment.getStudent().getFirstName();


		        String lastName =
		                payment.getStudent().getLastName();



		        receipt.setStudentName(
		                ((firstName != null ? firstName : "")
		                + " "
		                + (lastName != null ? lastName : ""))
		                .trim()
		        );


		        receipt.setRollNumber(
		                payment.getStudent().getRollNumber()
		        );
		    }



		    // ============================
		    // Admission Snapshot
		    // ============================

		    if(payment.getAdmission() != null){

		        receipt.setAdmissionId(
		                payment.getAdmission().getId()
		        );


		        receipt.setAdmissionNumber(
		                payment.getAdmission()
		                .getAdmissionNumber()
		        );



		        if(payment.getAdmission().getCourse() != null){

		            receipt.setCourseId(
		                    payment.getAdmission()
		                    .getCourse()
		                    .getId()
		            );


		            receipt.setCourseName(
		                    payment.getAdmission()
		                    .getCourse()
		                    .getCourseName()
		            );
		        }


		        if(payment.getAdmission().getDepartment() != null){

		            receipt.setDepartmentId(
		                    payment.getAdmission()
		                    .getDepartment()
		                    .getId()
		            );


		            receipt.setDepartmentName(
		                    payment.getAdmission()
		                    .getDepartment()
		                    .getDepartmentName()
		            );
		        }


		        if(payment.getAdmission().getSemester() != null){

		            receipt.setSemesterId(
		                    payment.getAdmission()
		                    .getSemester()
		                    .getId()
		            );


		            receipt.setSemesterName(
		                    payment.getAdmission()
		                    .getSemester()
		                    .getSemesterName()
		            );
		        }

		    }



		    // ============================
		    // Academic Year
		    // ============================

		    receipt.setAcademicYear(
		            payment.getAcademicYear()
		    );



		    // ============================
		    // Fee Snapshot
		    // ============================


		    receipt.setTotalFee(
		            payment.getTotalFee()
		    );


		    receipt.setPaidAmount(
		            payment.getPaidAmount()
		    );


		    receipt.setDueAmount(
		            payment.getDueAmount()
		    );


	
			 // ============================
			 // Scholarship Snapshot
			 // ============================
	
			 BigDecimal scholarship =
			         BigDecimal.ZERO;
	
	
			 if(payment.getScholarship() != null){
	
	
			     scholarship =
			             payment.getScholarship()
			             .getApprovedAmount();
	
	
			     receipt.setScholarshipId(
			             payment.getScholarship()
			             .getId()
			     );
	
	
			     receipt.setScholarshipAmount(
			             scholarship
			     );
	
			 }
			 else{
	
	
			     receipt.setScholarshipAmount(
			             BigDecimal.ZERO
			     );
	
			 }



		 // ============================
		 // Final Payable Amount
		 // ============================


		 receipt.setFinalPayableAmount(
		         payment.getTotalFee()
		         .subtract(
		                 scholarship
		         )
		 );



		    // ============================
		    // Payment Snapshot
		    // ============================


		    receipt.setPaymentStatus(
		            payment.getPaymentStatus()
		    );


		    receipt.setPaymentMode(
		            payment.getPaymentMode()
		    );


		    receipt.setPaymentDate(
		            payment.getPaymentDate()
		    );


		    receipt.setTransactionReference(
		            payment.getTransactionReference()
		    );

		}
		
		
		
		
		
		// =====================================================
		// Validate Receipt Snapshot
		// =====================================================


		private void validateReceiptSnapshot(
		        StudentFeeReceipt receipt
		){


		    if(receipt.getStudentId()==null){

		        throw new BusinessException(
		                "Student information missing for receipt"
		        );

		    }



		    if(receipt.getAdmissionId()==null){

		        throw new BusinessException(
		                "Admission information missing for receipt"
		        );

		    }



		    if(receipt.getAcademicYear()==null
		            || receipt.getAcademicYear().isBlank()){


		        throw new BusinessException(
		                "Academic year missing for receipt"
		        );

		    }



		    if(receipt.getTotalFee()==null){

		        throw new BusinessException(
		                "Total fee missing for receipt"
		        );

		    }



		    if(receipt.getPaidAmount()==null){

		        throw new BusinessException(
		                "Paid amount missing for receipt"
		        );

		    }



		    if(receipt.getDueAmount()==null){

		        throw new BusinessException(
		                "Due amount missing for receipt"
		        );

		    }



		    if(receipt.getPaymentStatus()==null){

		        throw new BusinessException(
		                "Payment status missing for receipt"
		        );

		    }

		}
		
		

		
		
		@Override
		@Transactional(readOnly = true)
		public byte[] printReceipt(Long id){


		    StudentFeeReceipt receipt =
		            receiptRepository
		            .findByIdAndStatusNot(
		                    id,
		                    RecordStatus.DELETED
		            )
		            .orElseThrow(() ->
		                    new ResourceNotFoundException(
		                            "Fee receipt not found"
		                    )
		            );


		    return receiptPdfService
		            .generateReceiptPdf(receipt);

		}

    
}