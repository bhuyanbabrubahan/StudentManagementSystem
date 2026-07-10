package com.sms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.location.address.entity.Address;
import com.sms.enums.StudentStatus;
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
//@SQLDelete(sql = "UPDATE students SET status='DELETED' WHERE id=?")
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="first_name",
			nullable = false,
			length = 50)
	private String firstName;

	@Column(
	        name = "last_name",
	        nullable = false,
	        length = 50)
	private String lastName;


	@Column(
	        name = "phone_number",
	        nullable = false,
	        length = 10)
	private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	
	@Column(name = "admission_date")
	private LocalDate admissionDate;
	
	@Column(
	        name = "roll_number",
	        nullable = false,
	        unique = true,
	        length = 20)
	private String rollNumber;
	
	@Column(name = "profile_image")	
	private String profileImage;
	
	@Column(nullable = false)
	private BigDecimal fees;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StudentStatus status;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", unique = true)
	@ToString.Exclude
	private User user;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="address_id")
	@ToString.Exclude
	private Address address;
	
	
}
