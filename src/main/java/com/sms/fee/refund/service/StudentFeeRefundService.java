package com.sms.fee.refund.service;


import com.sms.dto.PageResponse;
import com.sms.fee.refund.dto.StudentFeeRefundRequestDto;
import com.sms.fee.refund.dto.StudentFeeRefundResponseDto;
import com.sms.fee.refund.dto.StudentFeeRefundSearchRequest;



public interface StudentFeeRefundService {



    // =====================================================
    // Create Refund
    // =====================================================


    StudentFeeRefundResponseDto createRefund(
            StudentFeeRefundRequestDto request
    );





    // =====================================================
    // Get Refund By Id
    // =====================================================


    StudentFeeRefundResponseDto getRefundById(
            Long id
    );





    // =====================================================
    // Delete Refund (Soft Delete)
    // =====================================================


    void deleteRefund(
            Long id
    );





    // =====================================================
    // Get All Refunds Pagination
    // =====================================================


    PageResponse<StudentFeeRefundResponseDto> getAllRefunds(
            int page,
            int size,
            String sortBy,
            String direction
    );





    // =====================================================
    // Search Refunds
    // =====================================================


    PageResponse<StudentFeeRefundResponseDto> searchRefunds(
            StudentFeeRefundSearchRequest request,
            int page,
            int size,
            String sortBy,
            String direction
    );



}