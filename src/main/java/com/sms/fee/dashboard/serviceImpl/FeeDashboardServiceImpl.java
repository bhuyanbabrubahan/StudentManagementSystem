package com.sms.fee.dashboard.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.enums.RecordStatus;
import com.sms.fee.dashboard.dto.FeeDashboardResponseDto;
import com.sms.fee.dashboard.service.FeeDashboardService;
import com.sms.fee.enums.PaymentMode;
import com.sms.fee.enums.PaymentStatus;
import com.sms.fee.repository.StudentFeePaymentRepository;
import com.sms.scholarship.enums.ScholarshipStatus;
import com.sms.scholarship.repository.StudentScholarshipRepository;
import com.sms.fee.repository.StudentFeeReceiptRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeeDashboardServiceImpl
        implements FeeDashboardService {

    private final StudentFeePaymentRepository paymentRepository;

    private final StudentScholarshipRepository scholarshipRepository;
    
    private final StudentFeeReceiptRepository receiptRepository;

    @Override
    public FeeDashboardResponseDto getDashboard() {

        FeeDashboardResponseDto dto =
                new FeeDashboardResponseDto();

        LocalDate today =
                LocalDate.now();

        // =====================================================
        // Collection Summary
        // =====================================================

        dto.setTotalFeeCollection(
                paymentRepository.getTotalFeeCollection(
                        RecordStatus.DELETED
                )
        );

        dto.setTodayCollection(
                paymentRepository.getTodayCollection(
                        today,
                        RecordStatus.DELETED
                )
        );

        dto.setMonthlyCollection(
                paymentRepository.getMonthlyCollection(
                        today.getYear(),
                        today.getMonthValue(),
                        RecordStatus.DELETED
                )
        );

        dto.setTotalPendingDue(
                paymentRepository.getTotalPendingDue(
                        RecordStatus.DELETED
                )
        );

        // =====================================================
        // Payment Statistics
        // =====================================================

        dto.setTotalPayments(
                paymentRepository.countByStatusNot(
                        RecordStatus.DELETED
                )
        );

        dto.setPaidPayments(
                paymentRepository.countByPaymentStatusAndStatusNot(
                        PaymentStatus.PAID,
                        RecordStatus.DELETED
                )
        );

        dto.setPartialPayments(
                paymentRepository.countByPaymentStatusAndStatusNot(
                        PaymentStatus.PARTIAL,
                        RecordStatus.DELETED
                )
        );

        dto.setPendingPayments(
                paymentRepository.countByPaymentStatusAndStatusNot(
                        PaymentStatus.PENDING,
                        RecordStatus.DELETED
                )
        );

        dto.setCancelledPayments(
                paymentRepository.countByPaymentStatusAndStatusNot(
                        PaymentStatus.CANCELLED,
                        RecordStatus.DELETED
                )
        );

        // =====================================================
        // Payment Mode Collection
        // =====================================================

        dto.setCashCollection(
                paymentRepository.getCollectionByPaymentMode(
                        PaymentMode.CASH,
                        RecordStatus.DELETED
                )
        );

        dto.setOnlineCollection(
                paymentRepository.getCollectionByPaymentMode(
                        PaymentMode.ONLINE,
                        RecordStatus.DELETED
                )
        );

        dto.setUpiCollection(
                paymentRepository.getCollectionByPaymentMode(
                        PaymentMode.UPI,
                        RecordStatus.DELETED
                )
        );

        dto.setChequeCollection(
                paymentRepository.getCollectionByPaymentMode(
                        PaymentMode.CHEQUE,
                        RecordStatus.DELETED
                )
        );

        // =====================================================
        // Scholarship Summary
        // =====================================================

        dto.setTotalScholarshipAmount(
                scholarshipRepository.getTotalApprovedScholarshipAmount(
                        RecordStatus.DELETED,
                        ScholarshipStatus.APPROVED
                )
        );

        dto.setTotalScholarshipStudents(
                scholarshipRepository.countByScholarshipStatusAndStatusNot(
                        ScholarshipStatus.APPROVED,
                        RecordStatus.DELETED
                )
        );
        
        
        // =====================================================
        // Average Collection
        // =====================================================
        
        if(dto.getTotalPayments()!=null 
                && dto.getTotalPayments()>0){

            dto.setAverageCollection(
                    dto.getTotalFeeCollection()
                    .divide(
                        BigDecimal.valueOf(
                            dto.getTotalPayments()
                        ),
                        2,
                        RoundingMode.HALF_UP
                    )
            );

        }
        else{

            dto.setAverageCollection(
                    BigDecimal.ZERO
            );

        }
        
        // =====================================================
        // Collection Percentage
        // =====================================================
        
        BigDecimal totalPayable =
                paymentRepository.getTotalPayableFee(
                        RecordStatus.DELETED
                );
        
        if(totalPayable.compareTo(BigDecimal.ZERO)>0){

            dto.setCollectionPercentage(

                dto.getTotalFeeCollection()
                .multiply(
                     BigDecimal.valueOf(100)
                )
                .divide(
                     totalPayable,
                     2,
                     RoundingMode.HALF_UP
                )

            );

        }
        else{

            dto.setCollectionPercentage(
                    BigDecimal.ZERO
            );

        }
        
        
        
     // =====================================================
     // Receipt Summary
     // =====================================================

     dto.setTotalReceipts(
             receiptRepository.countByStatusNot(
                     RecordStatus.DELETED
             )
     );
        

        return dto;
    }

}