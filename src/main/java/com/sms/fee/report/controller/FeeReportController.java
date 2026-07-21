package com.sms.fee.report.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.fee.report.dto.AcademicYearWiseFeeReportDto;
import com.sms.fee.report.dto.CourseWiseReportDto;
import com.sms.fee.report.dto.DepartmentFeeReportDto;
import com.sms.fee.report.dto.MonthlyCollectionReportDto;
import com.sms.fee.report.dto.PaymentModeReportDto;
import com.sms.fee.report.dto.PendingDueReportDto;
import com.sms.fee.report.service.FeeReportService;
import com.sms.payload.ApiResponseDto;
import com.sms.util.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fee-reports")
@Tag(
        name = "Fee Reports",
        description = 
        "APIs for generating fee collection and pending due reports"
)
public class FeeReportController {



    private final FeeReportService feeReportService;




    // =====================================================
    // Department Wise Report
    // =====================================================


    @GetMapping("/department-wise")
    @Operation(
            summary = "Department Wise Fee Report",
            description =
            "Returns total fee collection department wise"
    )
    public ResponseEntity<ApiResponseDto<List<DepartmentFeeReportDto>>> 
    getDepartmentWiseReport(){



        return ResponseBuilder.success(

                feeReportService.getDepartmentWiseReport(),

                "Department wise fee report generated successfully",

                HttpStatus.OK
        );

    }







    // =====================================================
    // Course Wise Report
    // =====================================================


    @GetMapping("/course-wise")
    @Operation(
            summary = "Course Wise Fee Report",
            description =
            "Returns total fee collection course wise"
    )
    public ResponseEntity<ApiResponseDto<List<CourseWiseReportDto>>> 
    getCourseWiseReport(){



        return ResponseBuilder.success(

                feeReportService.getCourseWiseReport(),

                "Course wise fee report generated successfully",

                HttpStatus.OK
        );

    }








    // =====================================================
    // Academic Year Wise Report
    // =====================================================


    @GetMapping("/academic-year-wise")
    @Operation(
            summary = "Academic Year Wise Fee Report",
            description =
            "Returns fee collection academic year wise"
    )
    public ResponseEntity<ApiResponseDto<List<AcademicYearWiseFeeReportDto>>> 
    getAcademicYearWiseReport(){



        return ResponseBuilder.success(

                feeReportService.getAcademicYearWiseReport(),

                "Academic year wise fee report generated successfully",

                HttpStatus.OK
        );

    }








    // =====================================================
    // Payment Mode Report
    // =====================================================


    @GetMapping("/payment-mode")
    @Operation(
            summary = "Payment Mode Collection Report",
            description =
            "Returns collection based on payment mode like CASH, ONLINE, UPI and CHEQUE"
    )
    public ResponseEntity<ApiResponseDto<List<PaymentModeReportDto>>> 
    getPaymentModeReport(){



        return ResponseBuilder.success(

                feeReportService.getPaymentModeReport(),

                "Payment mode report generated successfully",

                HttpStatus.OK
        );

    }








    // =====================================================
    // Monthly Collection Report
    // =====================================================


    @GetMapping("/monthly-collection")
    @Operation(
            summary = "Monthly Collection Report",
            description =
            "Returns month wise fee collection summary"
    )
    public ResponseEntity<ApiResponseDto<List<MonthlyCollectionReportDto>>> 
    getMonthlyCollectionReport(){



        return ResponseBuilder.success(

                feeReportService.getMonthlyCollectionReport(),

                "Monthly collection report generated successfully",

                HttpStatus.OK
        );

    }









    // =====================================================
    // Pending Due Report
    // =====================================================


    @GetMapping("/pending-dues")
    @Operation(
            summary = "Pending Fee Due Report",
            description =
            "Returns students having pending fee dues"
    )
    public ResponseEntity<ApiResponseDto<List<PendingDueReportDto>>> 
    getPendingDueReport(){



        return ResponseBuilder.success(

                feeReportService.getPendingDueReport(),

                "Pending due report generated successfully",

                HttpStatus.OK
        );

    }



}