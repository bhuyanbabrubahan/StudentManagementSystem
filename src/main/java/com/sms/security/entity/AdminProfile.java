package com.sms.security.entity;

import com.sms.common.entity.BaseEntity;
import com.sms.enums.RecordStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admin_profiles")
@Getter
@Setter
@NoArgsConstructor
public class AdminProfile extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


	private String firstName;

	private String lastName;

	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AdminDesignation designation;

	private String profileImage;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        unique = true,
        nullable = false
    )
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private RecordStatus status;

}