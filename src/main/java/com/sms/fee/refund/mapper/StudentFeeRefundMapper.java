package com.sms.fee.refund.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.sms.fee.refund.dto.StudentFeeRefundRequestDto;
import com.sms.fee.refund.dto.StudentFeeRefundResponseDto;
import com.sms.fee.refund.entity.StudentFeeRefund;



@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StudentFeeRefundMapper {



    // =====================================================
    // Request DTO -> Entity
    // =====================================================


    @Mapping(
            target = "id",
            ignore = true
    )
    @Mapping(
            target = "payment",
            ignore = true
    )
    @Mapping(
            target = "receipt",
            ignore = true
    )
    @Mapping(
            target = "refundNumber",
            ignore = true
    )
    @Mapping(
            target = "refundDate",
            ignore = true
    )
    @Mapping(
            target = "studentId",
            ignore = true
    )
    @Mapping(
            target = "studentName",
            ignore = true
    )
    @Mapping(
            target = "rollNumber",
            ignore = true
    )
    @Mapping(
            target = "admissionId",
            ignore = true
    )
    @Mapping(
            target = "admissionNumber",
            ignore = true
    )
    @Mapping(
            target = "transactionReference",
            ignore = true
    )
    @Mapping(
            target = "refundStatus",
            ignore = true
    )
    @Mapping(
            target = "status",
            ignore = true
    )
    StudentFeeRefund toEntity(
            StudentFeeRefundRequestDto dto
    );





    // =====================================================
    // Entity -> Response DTO
    // =====================================================


    @Mapping(
            target = "paymentId",
            source = "payment.id"
    )
    @Mapping(
            target = "receiptNumber",
            source = "receipt.receiptNumber"
    )
    StudentFeeRefundResponseDto toDto(
            StudentFeeRefund entity
    );



}