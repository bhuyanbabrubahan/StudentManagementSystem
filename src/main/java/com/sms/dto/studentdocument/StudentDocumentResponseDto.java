package com.sms.dto.studentdocument;

import java.time.LocalDateTime;

import com.sms.enums.DocumentType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "StudentDocumentResponse",
        description = "Response DTO for student uploaded document"
)
public class StudentDocumentResponseDto {


    @Schema(
            description = "Unique document id",
            example = "1"
    )
    private Long id;


    @Schema(
            description = "Student id who uploaded the document",
            example = "101"
    )
    private Long studentId;


    @Schema(
            description = "Type of uploaded document",
            example = "PROFILE_PHOTO"
    )
    private DocumentType documentType;


    @Schema(
            description = "Original file name uploaded by student",
            example = "aadhar_card.pdf"
    )
    private String originalFileName;


    @Schema(
            description = "Generated unique stored file name",
            example = "8c7f1c8a-2d3b-4f1e-a123.pdf"
    )
    private String storedFileName;


    @Schema(
            description = "Uploaded file content type",
            example = "application/pdf"
    )
    private String contentType;


    @Schema(
            description = "Size of uploaded file in bytes",
            example = "204800"
    )
    private Long fileSize;


    @Schema(
            description = "Document upload timestamp",
            example = "2026-07-19T10:30:00"
    )
    private LocalDateTime uploadedAt;

}