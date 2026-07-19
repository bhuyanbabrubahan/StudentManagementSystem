package com.sms.entity;


import java.time.LocalDateTime;

import com.sms.common.entity.BaseEntity;
import com.sms.enums.DocumentType;
import com.sms.enums.RecordStatus;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "student_documents"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDocument extends BaseEntity {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;



    // Which student uploaded document

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    private Student student;



    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false
    )
    private DocumentType documentType;



    @Column(
            nullable = false
    )
    private String originalFileName;



    @Column(
            nullable = false,
            unique = true
    )
    private String storedFileName;



    private String filePath;



    private String contentType;



    private Long fileSize;



    private LocalDateTime uploadedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private RecordStatus status;

}