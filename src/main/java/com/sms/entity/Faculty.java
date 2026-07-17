package com.sms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.location.address.entity.Address;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "faculties")
@Getter
@Setter
@NoArgsConstructor
public class Faculty extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(
            name = "employee_code",
            nullable = false,
            unique = true
    )
    private String employeeCode;


    @Column(
            name="first_name",
            nullable=false
    )
    private String firstName;


    @Column(
            name="last_name",
            nullable=false
    )
    private String lastName;


    @Column(
            name="phone_number",
            nullable=false,
            length=10
    )
    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    private Gender gender;


    private LocalDate dateOfBirth;


    @Column(nullable=false)
    private LocalDate joiningDate;


    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Designation designation;


    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Qualification qualification;


    @Column(nullable=false)
    private BigDecimal salary;


    private String profileImage;



    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private RecordStatus status;



    // Department Relation

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="department_id",
            nullable=false
    )
    @ToString.Exclude
    private Department department;



    // Address Relation

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="address_id",
            nullable=false
    )
    @ToString.Exclude
    private Address address;



    // User Relation

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="user_id",
            unique=true,
            nullable=false
    )
    @ToString.Exclude
    private User user;
    
    
    @JsonIgnore
    @OneToMany(
            mappedBy = "faculty",
            fetch = FetchType.LAZY
    )
    private List<FacultySubjectMapping> subjectMappings =
            new ArrayList<>();


}