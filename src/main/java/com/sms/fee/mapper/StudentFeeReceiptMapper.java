package com.sms.fee.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


import com.sms.fee.dto.StudentFeeReceiptRequestDto;
import com.sms.fee.dto.StudentFeeReceiptResponseDto;
import com.sms.fee.entity.StudentFeeReceipt;



@Mapper(componentModel = "spring")
public interface StudentFeeReceiptMapper {



    // =====================================================
    // Entity -> Response DTO
    // =====================================================


    @Mapping(target = "paymentId",
            source = "payment.id")


    // Receipt Snapshot

    @Mapping(target = "studentId",
            source = "studentId")

    @Mapping(target = "studentName",
            source = "studentName")

    @Mapping(target = "rollNumber",
            source = "rollNumber")



    @Mapping(target = "admissionId",
            source = "admissionId")

    @Mapping(target = "admissionNumber",
            source = "admissionNumber")


    @Mapping(target = "academicYear",
            source = "academicYear")



    @Mapping(target = "courseId",
            source = "courseId")


    @Mapping(target = "courseName",
            source = "courseName")



    @Mapping(target = "departmentId",
            source = "departmentId")


    @Mapping(target = "departmentName",
            source = "departmentName")



    @Mapping(target = "semesterId",
            source = "semesterId")


    @Mapping(target = "semesterName",
            source = "semesterName")



    @Mapping(target = "scholarshipId",
            source = "scholarshipId")


    @Mapping(target = "scholarshipAmount",
            source = "scholarshipAmount")



    @Mapping(target = "totalFee",
            source = "totalFee")


    @Mapping(target = "finalPayableAmount",
            source = "finalPayableAmount")


    @Mapping(target = "paidAmount",
            source = "paidAmount")


    @Mapping(target = "dueAmount",
            source = "dueAmount")



    @Mapping(target = "paymentStatus",
            source = "paymentStatus")


    @Mapping(target = "paymentMode",
            source = "paymentMode")


    @Mapping(target = "paymentDate",
            source = "paymentDate")


    @Mapping(target = "transactionReference",
            source = "transactionReference")



    StudentFeeReceiptResponseDto toDto(
            StudentFeeReceipt entity
    );



	// =====================================================
	// Request DTO -> Entity
	// =====================================================

	@Mapping(target = "id", ignore = true)

	@Mapping(target = "payment", ignore = true)

	@Mapping(target = "receiptNumber", ignore = true)

	@Mapping(target = "status", ignore = true)

	@Mapping(target = "studentId", ignore = true)

	@Mapping(target = "studentName", ignore = true)

	@Mapping(target = "rollNumber", ignore = true)

	@Mapping(target = "admissionId", ignore = true)

	@Mapping(target = "admissionNumber", ignore = true)

	@Mapping(target = "academicYear", ignore = true)

	@Mapping(target = "courseId", ignore = true)

	@Mapping(target = "courseName", ignore = true)

	@Mapping(target = "departmentId", ignore = true)

	@Mapping(target = "departmentName", ignore = true)

	@Mapping(target = "semesterId", ignore = true)

	@Mapping(target = "semesterName", ignore = true)

	@Mapping(target = "scholarshipId", ignore = true)

	@Mapping(target = "scholarshipAmount", ignore = true)

	@Mapping(target = "totalFee", ignore = true)

	@Mapping(target = "finalPayableAmount", ignore = true)

	@Mapping(target = "paidAmount", ignore = true)

	@Mapping(target = "dueAmount", ignore = true)

	@Mapping(target = "paymentStatus", ignore = true)

	@Mapping(target = "paymentMode", ignore = true)

	@Mapping(target = "paymentDate", ignore = true)

	@Mapping(target = "transactionReference", ignore = true)

	@Mapping(target = "createdAt", ignore = true)

	@Mapping(target = "updatedAt", ignore = true)

	StudentFeeReceipt toEntity(StudentFeeReceiptRequestDto dto);





	// =====================================================
	// Update Entity
	// =====================================================

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

	@Mapping(target = "id", ignore = true)

	@Mapping(target = "payment", ignore = true)

	@Mapping(target = "receiptNumber", ignore = true)

	@Mapping(target = "status", ignore = true)

	@Mapping(target = "studentId", ignore = true)
	@Mapping(target = "studentName", ignore = true)
	@Mapping(target = "rollNumber", ignore = true)

	@Mapping(target = "admissionId", ignore = true)
	@Mapping(target = "admissionNumber", ignore = true)

	@Mapping(target = "academicYear", ignore = true)

	@Mapping(target = "courseId", ignore = true)
	@Mapping(target = "courseName", ignore = true)

	@Mapping(target = "departmentId", ignore = true)
	@Mapping(target = "departmentName", ignore = true)

	@Mapping(target = "semesterId", ignore = true)
	@Mapping(target = "semesterName", ignore = true)

	@Mapping(target = "scholarshipId", ignore = true)
	@Mapping(target = "scholarshipAmount", ignore = true)

	@Mapping(target = "totalFee", ignore = true)
	@Mapping(target = "finalPayableAmount", ignore = true)
	@Mapping(target = "paidAmount", ignore = true)
	@Mapping(target = "dueAmount", ignore = true)

	@Mapping(target = "paymentStatus", ignore = true)
	@Mapping(target = "paymentMode", ignore = true)
	@Mapping(target = "paymentDate", ignore = true)
	@Mapping(target = "transactionReference", ignore = true)

	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)

	void updateEntity(@MappingTarget StudentFeeReceipt entity, StudentFeeReceiptRequestDto dto);


}