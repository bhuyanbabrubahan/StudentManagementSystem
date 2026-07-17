package com.sms.dto;

import com.sms.entity.Designation;
import com.sms.entity.Qualification;
import com.sms.enums.RecordStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "FacultySearchRequest",
    description = "Request DTO used for searching faculty records using multiple optional filters."
)
public class FacultySearchRequest {

    @Schema(
        description = "Search by Faculty ID",
        example = "1"
    )
    private Long id;

    @Schema(
        description = "Search by faculty first name (supports partial and case-insensitive search)",
        example = "Rahul"
    )
    private String firstName;

    @Schema(
        description = "Search by faculty last name (supports partial and case-insensitive search)",
        example = "Sharma"
    )
    private String lastName;

    @Schema(
        description = "Search by employee code",
        example = "FAC0001"
    )
    private String employeeCode;

    @Schema(
        description = "Search by department ID",
        example = "2"
    )
    private Long departmentId;

    @Schema(
        description = "Search by faculty designation",
        example = "ASSISTANT_PROFESSOR",
        allowableValues = {
            "PROFESSOR",
            "ASSOCIATE_PROFESSOR",
            "ASSISTANT_PROFESSOR",
            "LECTURER",
            "HOD"
        }
    )
    private Designation designation;

    @Schema(
        description = "Search by faculty qualification",
        example = "MTECH",
        allowableValues = {
            "BTECH",
            "MTECH",
            "MSC",
            "MCA",
            "MBA",
            "PHD"
        }
    )
    private Qualification qualification;

    @Schema(
        description = "Search by record status. By default ACTIVE records are returned.",
        example = "ACTIVE",
        allowableValues = {
            "ACTIVE",
            "INACTIVE",
            "DELETED"
        }
    )
    private RecordStatus status;

}