package com.sms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.location.address.entity.Address;
import com.sms.common.entity.BaseEntity;
import com.sms.enums.RecordStatus;
import com.sms.security.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	@Column(
		    name = "phone_number",
		    nullable = false,
		    unique = true,
		    length = 10
		)
		private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;

	@Column(
		    name="date_of_birth",
		    nullable=false
		)
	private LocalDate dateOfBirth;

	@Column(
		    name="admission_date",
		    nullable=false
		)
	private LocalDate admissionDate;

	@Column(
		    name = "roll_number",
		    nullable = false,
		    unique = true,
		    length = 20
		)
	private String rollNumber;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(
		    nullable=false,
		    precision=10,
		    scale=2
		)
		private BigDecimal fees;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private RecordStatus status;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(
	        name = "user_id",
	        unique = true
	)
	@ToString.Exclude
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	@ToString.Exclude
	private Address address;
	
	
	
	public String getFullName() {

	    return Stream.of(firstName,lastName)
	            .filter(Objects::nonNull)
	            .map(String::trim)
	            .filter(s->!s.isBlank())
	            .collect(Collectors.joining(" "));
	}

}
