package com.sms.fee.report.serviceImpl;


import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.enums.RecordStatus;
import com.sms.fee.report.dto.AcademicYearWiseFeeReportDto;
import com.sms.fee.report.dto.CourseWiseReportDto;
import com.sms.fee.report.dto.DepartmentFeeReportDto;
import com.sms.fee.report.dto.MonthlyCollectionReportDto;
import com.sms.fee.report.dto.PaymentModeReportDto;
import com.sms.fee.report.dto.PendingDueReportDto;
import com.sms.fee.report.repository.FeeReportRepository;
import com.sms.fee.report.service.FeeReportService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeeReportServiceImpl
        implements FeeReportService {



    private final FeeReportRepository feeReportRepository;



    // =====================================================
    // Department Wise Fee Report
    // =====================================================


    @Override
    public List<DepartmentFeeReportDto> getDepartmentWiseReport() {



        List<Object[]> result =
                feeReportRepository
                .getDepartmentWiseCollection(
                        RecordStatus.DELETED
                );



        List<DepartmentFeeReportDto> response =
                new ArrayList<>();



        for(Object[] row : result){


            DepartmentFeeReportDto dto =
                    new DepartmentFeeReportDto();



            dto.setDepartmentId(
                    row[0] != null
                    ?
                    ((Number)row[0]).longValue()
                    :
                    null
            );



            dto.setDepartmentName(
                    row[1] != null
                    ?
                    row[1].toString()
                    :
                    null
            );



            dto.setTotalStudents(
                    row[2] != null
                    ?
                    ((Number)row[2]).longValue()
                    :
                    0L
            );



            dto.setTotalFee(
                    row[3] != null
                    ?
                    (BigDecimal)row[3]
                    :
                    BigDecimal.ZERO
            );



            dto.setCollectedAmount(
                    row[4] != null
                    ?
                    (BigDecimal)row[4]
                    :
                    BigDecimal.ZERO
            );



            dto.setPendingAmount(
                    row[5] != null
                    ?
                    (BigDecimal)row[5]
                    :
                    BigDecimal.ZERO
            );



            response.add(dto);

        }



        return response;

    }



	 // =====================================================
	 // Course Wise Fee Report
	 // =====================================================
	
	
	 @Override
	 public List<CourseWiseReportDto> getCourseWiseReport() {
	
	
	     List<Object[]> result =
	             feeReportRepository
	             .getCourseWiseCollection(
	                     RecordStatus.DELETED
	             );
	
	
	
	     List<CourseWiseReportDto> response =
	             new ArrayList<>();
	
	
	
	     for(Object[] row : result){
	
	
	
	         CourseWiseReportDto dto =
	                 new CourseWiseReportDto();
	
	
	
	         dto.setCourseId(
	                 row[0] != null
	                 ?
	                 ((Number)row[0]).longValue()
	                 :
	                 null
	         );
	
	
	
	         dto.setCourseName(
	                 row[1] != null
	                 ?
	                 row[1].toString()
	                 :
	                 null
	         );
	
	
	
	         dto.setDepartmentName(
	                 row[2] != null
	                 ?
	                 row[2].toString()
	                 :
	                 null
	         );
	
	
	
	         dto.setTotalStudents(
	                 row[3] != null
	                 ?
	                 ((Number)row[3]).longValue()
	                 :
	                 0L
	         );
	
	
	
	         dto.setTotalFee(
	                 row[4] != null
	                 ?
	                 (BigDecimal) row[4]
	                 :
	                 BigDecimal.ZERO
	         );
	
	
	
	         dto.setCollectedAmount(
	                 row[5] != null
	                 ?
	                 (BigDecimal) row[5]
	                 :
	                 BigDecimal.ZERO
	         );
	
	
	
	         dto.setPendingAmount(
	                 row[6] != null
	                 ?
	                 (BigDecimal) row[6]
	                 :
	                 BigDecimal.ZERO
	         );
	
	
	
	         response.add(dto);
	
	     }
	
	
	
	     return response;
	
	 }



	// =====================================================
	// Academic Year Wise Fee Report
	// =====================================================


	@Override
	public List<AcademicYearWiseFeeReportDto> getAcademicYearWiseReport() {



	    List<Object[]> result =
	            feeReportRepository
	            .getAcademicYearWiseCollection(
	                    RecordStatus.DELETED
	            );



	    List<AcademicYearWiseFeeReportDto> response =
	            new ArrayList<>();



	    for(Object[] row : result){



	        AcademicYearWiseFeeReportDto dto =
	                new AcademicYearWiseFeeReportDto();




	        // Academic Year

	        dto.setAcademicYear(
	                row[0] != null
	                ?
	                row[0].toString()
	                :
	                null
	        );




	        // Total Students

	        dto.setTotalStudents(
	                row[1] != null
	                ?
	                ((Number)row[1]).longValue()
	                :
	                0L
	        );




	        // Total Fee

	        dto.setTotalFee(
	                row[2] != null
	                ?
	                (BigDecimal)row[2]
	                :
	                BigDecimal.ZERO
	        );




	        // Collected Amount

	        dto.setCollectedAmount(
	                row[3] != null
	                ?
	                (BigDecimal)row[3]
	                :
	                BigDecimal.ZERO
	        );




	        // Pending Amount

	        dto.setPendingAmount(
	                row[4] != null
	                ?
	                (BigDecimal)row[4]
	                :
	                BigDecimal.ZERO
	        );




	        // Total Payments

	        dto.setTotalPayments(
	                row[5] != null
	                ?
	                ((Number)row[5]).longValue()
	                :
	                0L
	        );




	        response.add(dto);

	    }



	    return response;

	}



	// =====================================================
	// Payment Mode Wise Report
	// =====================================================


	@Override
	public List<PaymentModeReportDto> getPaymentModeReport() {



	    List<Object[]> result =
	            feeReportRepository
	            .getPaymentModeWiseCollection(
	                    RecordStatus.DELETED
	            );



	    List<PaymentModeReportDto> response =
	            new ArrayList<>();



	    for(Object[] row : result){



	        PaymentModeReportDto dto =
	                new PaymentModeReportDto();




	        // Payment Mode

	        dto.setPaymentMode(
	                row[0] != null
	                ?
	                row[0].toString()
	                :
	                null
	        );




	        // Total Transactions

	        dto.setTotalTransactions(
	                row[1] != null
	                ?
	                ((Number)row[1]).longValue()
	                :
	                0L
	        );




	        // Total Collection

	        dto.setTotalCollection(
	                row[2] != null
	                ?
	                (BigDecimal)row[2]
	                :
	                BigDecimal.ZERO
	        );




	        response.add(dto);

	    }



	    return response;

	}



	// =====================================================
	// Monthly Collection Report
	// =====================================================


	@Override
	public List<MonthlyCollectionReportDto> getMonthlyCollectionReport() {



	    List<Object[]> result =
	            feeReportRepository
	            .getMonthlyCollection(
	                    RecordStatus.DELETED
	            );



	    List<MonthlyCollectionReportDto> response =
	            new ArrayList<>();



	    for(Object[] row : result){



	        MonthlyCollectionReportDto dto =
	                new MonthlyCollectionReportDto();




	        // Month Number

	        Integer month =
	                row[0] != null
	                ?
	                ((Number)row[0]).intValue()
	                :
	                null;



	        dto.setMonth(month);




	        // Month Name

	        if(month != null){

	            dto.setMonthName(
	                    Month.of(month)
	                    .name()
	            );

	        }




	        // Year

	        dto.setYear(
	                row[1] != null
	                ?
	                ((Number)row[1]).intValue()
	                :
	                null
	        );




	        // Total Transactions

	        dto.setTotalTransactions(
	                row[2] != null
	                ?
	                ((Number)row[2]).longValue()
	                :
	                0L
	        );




	        // Total Collection

	        dto.setTotalCollection(
	                row[3] != null
	                ?
	                (BigDecimal)row[3]
	                :
	                BigDecimal.ZERO
	        );




	        response.add(dto);

	    }



	    return response;

	}



	// =====================================================
	// Pending Due Report
	// =====================================================


	@Override
	public List<PendingDueReportDto> getPendingDueReport() {



	    List<Object[]> result =
	            feeReportRepository
	            .getPendingDueReport(
	                    RecordStatus.DELETED
	            );



	    List<PendingDueReportDto> response =
	            new ArrayList<>();



	    for(Object[] row : result){



	        PendingDueReportDto dto =
	                new PendingDueReportDto();




	        // Student Id

	        dto.setStudentId(
	                row[0] != null
	                ?
	                ((Number)row[0]).longValue()
	                :
	                null
	        );




	        // Student Name

	        dto.setStudentName(
	                row[1] != null
	                ?
	                row[1].toString()
	                :
	                null
	        );




	        // Roll Number

	        dto.setRollNumber(
	                row[2] != null
	                ?
	                row[2].toString()
	                :
	                null
	        );




	        // Admission Id

	        dto.setAdmissionId(
	                row[3] != null
	                ?
	                ((Number)row[3]).longValue()
	                :
	                null
	        );




	        // Admission Number

	        dto.setAdmissionNumber(
	                row[4] != null
	                ?
	                row[4].toString()
	                :
	                null
	        );




	        // Course Name

	        dto.setCourseName(
	                row[5] != null
	                ?
	                row[5].toString()
	                :
	                null
	        );




	        // Department Name

	        dto.setDepartmentName(
	                row[6] != null
	                ?
	                row[6].toString()
	                :
	                null
	        );




	        // Academic Year

	        dto.setAcademicYear(
	                row[7] != null
	                ?
	                row[7].toString()
	                :
	                null
	        );




	        // Total Fee

	        dto.setTotalFee(
	                row[8] != null
	                ?
	                (BigDecimal)row[8]
	                :
	                BigDecimal.ZERO
	        );




	        // Paid Amount

	        dto.setPaidAmount(
	                row[9] != null
	                ?
	                (BigDecimal)row[9]
	                :
	                BigDecimal.ZERO
	        );




	        // Due Amount

	        dto.setDueAmount(
	                row[10] != null
	                ?
	                (BigDecimal)row[10]
	                :
	                BigDecimal.ZERO
	        );




	        response.add(dto);

	    }



	    return response;

	}

}