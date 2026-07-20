package com.sms.fee.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


import com.sms.fee.dto.StudentFeePaymentRequestDto;
import com.sms.fee.dto.StudentFeePaymentResponseDto;
import com.sms.fee.entity.StudentFeePayment;



@Mapper(componentModel = "spring")
public interface StudentFeePaymentMapper {


    // =====================================================
    // DTO -> ENTITY
    // =====================================================


    @Mapping(
        target = "id",
        ignore = true
    )

    @Mapping(
        target = "student",
        ignore = true
    )

    @Mapping(
        target = "admission",
        ignore = true
    )

    @Mapping(
        target = "totalFee",
        ignore = true
    )

    @Mapping(
        target = "dueAmount",
        ignore = true
    )

    @Mapping(
        target = "paymentStatus",
        ignore = true
    )

    @Mapping(
        target = "status",
        ignore = true
    )
    
    @Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)

    StudentFeePayment toEntity(
            StudentFeePaymentRequestDto dto
    );




    // =====================================================
    // ENTITY -> DTO
    // =====================================================


    @Mapping(
        source = "student.id",
        target = "studentId"
    )


    @Mapping(
        source = "student.firstName",
        target = "studentName"
    )


    @Mapping(
        source = "admission.id",
        target = "admissionId"
    )


    StudentFeePaymentResponseDto toDto(
            StudentFeePayment entity
    );





    // =====================================================
    // UPDATE
    // =====================================================


    @BeanMapping(
        nullValuePropertyMappingStrategy =
        NullValuePropertyMappingStrategy.IGNORE
    )


    @Mapping(
        target = "id",
        ignore = true
    )


    @Mapping(
        target = "student",
        ignore = true
    )


    @Mapping(
        target = "admission",
        ignore = true
    )


    @Mapping(
        target = "totalFee",
        ignore = true
    )


    @Mapping(
        target = "dueAmount",
        ignore = true
    )


    @Mapping(
        target = "paymentStatus",
        ignore = true
    )


    @Mapping(
        target = "status",
        ignore = true
    )

    @Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)

    void updateEntity(
            @MappingTarget StudentFeePayment payment,
            StudentFeePaymentRequestDto dto
    );


}