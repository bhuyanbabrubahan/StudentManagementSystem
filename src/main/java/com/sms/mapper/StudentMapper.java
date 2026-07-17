package com.sms.mapper;

import org.springframework.stereotype.Component;

import com.location.address.entity.Address;
import com.location.country.entity.Country;
import com.location.district.entity.District;
import com.location.state.entity.State;
import com.location.tehsil.entity.Tehsil;
import com.location.village.entity.Village;
import com.sms.dto.StudentRequestDto;
import com.sms.dto.StudentResponseDto;
import com.sms.entity.Department;
import com.sms.entity.Student;
import com.sms.security.dto.RegisterRequestDto;

@Component
public class StudentMapper {

    // ==========================================================
    // StudentRequestDto -> Student Entity
    // ==========================================================

	public Student convertToEntity(StudentRequestDto dto) {

	    Student student = new Student();

	    student.setFirstName(dto.getFirstName().trim());

	    student.setLastName(dto.getLastName().trim());

	    student.setPhoneNumber(dto.getPhoneNumber().trim());

	    student.setGender(dto.getGender());

	    student.setDateOfBirth(dto.getDateOfBirth());

	    student.setAdmissionDate(dto.getAdmissionDate());

	    student.setFees(dto.getFees());

	    student.setProfileImage(dto.getProfileImage());

	    return student;
	}

    // ==========================================================
    // RegisterRequestDto -> Student Entity
    // ==========================================================

	public Student convertToEntity(RegisterRequestDto dto) {

	    Student student = new Student();

	    student.setFirstName(dto.getFirstName());
	    student.setLastName(dto.getLastName());
	    student.setPhoneNumber(dto.getPhoneNumber());
	    student.setGender(dto.getGender());

	    student.setDateOfBirth(dto.getDateOfBirth());

	    student.setAdmissionDate(dto.getAdmissionDate());

	    student.setFees(dto.getFees());

	    student.setProfileImage(dto.getProfileImage());

	    return student;
	}
    // ==========================================================
    // Student Entity -> Response DTO
    // ==========================================================

    public StudentResponseDto convertToResponseDto(Student student) {

        StudentResponseDto dto = new StudentResponseDto();

        // ==========================================================
        // Basic Details
        // ==========================================================

        dto.setId(student.getId());
        dto.setRollNumber(student.getRollNumber());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setFullName(
                student.getFullName()
        );
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setGender(student.getGender());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setAdmissionDate(student.getAdmissionDate());
        dto.setFees(student.getFees());
        dto.setProfileImage(student.getProfileImage());
        dto.setStatus(student.getStatus());

        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());

        // ==========================================================
        // Department Details
        // ==========================================================

        Department department = student.getDepartment();

        if (department != null) {

            dto.setDepartmentId(department.getId());

            dto.setDepartmentName(department.getDepartmentName());
        }

        // ==========================================================
        // Address Details
        // ==========================================================

		Address address = student.getAddress();

		if (address != null) {

			dto.setHouseNumber(address.getHouseNumber());

			dto.setStreet(address.getStreet());

			dto.setLandmark(address.getLandmark());

			dto.setAddressType(address.getAddressType());

			Village village = address.getVillage();

			if (village != null) {

			    dto.setVillage(village.getVillageName());

			    dto.setPincode(village.getPincode());

			    Tehsil tehsil = village.getTehsil();

			    if (tehsil != null) {

			        dto.setTehsil(tehsil.getTehsilName());

			        District district = tehsil.getDistrict();

			        if (district != null) {

			            dto.setDistrict(district.getDistrictName());

			            State state = district.getState();

			            if (state != null) {

			                dto.setState(state.getStateName());

			                Country country = state.getCountry();

			                if (country != null) {

			                    dto.setCountry(country.getCountryName());

			                }
			            }
			        }
			    }
			}
		}

		return dto;
	}

    // ==========================================================
    // Update Existing Student
    // ==========================================================

    public void updateEntity(
            Student student,
            StudentRequestDto dto
    ) {

        student.setFirstName(dto.getFirstName().trim());

        student.setLastName(dto.getLastName().trim());

        student.setPhoneNumber(dto.getPhoneNumber().trim());

        student.setGender(dto.getGender());

        student.setDateOfBirth(dto.getDateOfBirth());

        student.setAdmissionDate(dto.getAdmissionDate());

        student.setFees(dto.getFees());

        student.setProfileImage(dto.getProfileImage());

    }

}