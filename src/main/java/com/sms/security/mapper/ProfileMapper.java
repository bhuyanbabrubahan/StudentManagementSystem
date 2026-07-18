package com.sms.security.mapper;

import org.springframework.stereotype.Component;

import com.location.address.entity.Address;
import com.location.country.entity.Country;
import com.location.district.entity.District;
import com.location.state.entity.State;
import com.location.tehsil.entity.Tehsil;
import com.location.village.entity.Village;
import com.sms.entity.Department;
import com.sms.entity.Faculty;
import com.sms.entity.Student;
import com.sms.security.dto.ProfileResponseDto;
import com.sms.security.entity.AdminProfile;
import com.sms.security.entity.User;

@Component
public class ProfileMapper {

	// ==========================================================
	// STUDENT PROFILE
	// ==========================================================

	public ProfileResponseDto convertToDto(Student student) {

		ProfileResponseDto dto = new ProfileResponseDto();

		// User Information

		User user = student.getUser();

		setUserInformation(dto, user);

		// Student Information

		dto.setStudentId(student.getId());

		dto.setRollNumber(student.getRollNumber());

		dto.setFirstName(student.getFirstName());

		dto.setLastName(student.getLastName());

		dto.setFullName(student.getFullName());

		dto.setPhoneNumber(student.getPhoneNumber());

		dto.setGender(student.getGender());

		dto.setDateOfBirth(student.getDateOfBirth());

		dto.setAdmissionDate(student.getAdmissionDate());

		dto.setFees(student.getFees());

		dto.setProfileImage(student.getProfileImage());

		dto.setStatus(student.getStatus());

		// Department

		setDepartmentInformation(dto, student.getDepartment());

		// Address

		setAddressInformation(dto, student.getAddress());

		return dto;

	}

	// ==========================================================
	// FACULTY PROFILE
	// ==========================================================

	public ProfileResponseDto convertFacultyToDto(Faculty faculty) {

		ProfileResponseDto dto = new ProfileResponseDto();

		// User

		setUserInformation(dto, faculty.getUser());

		// Faculty Information

		dto.setFacultyId(faculty.getId());

		dto.setEmployeeCode(faculty.getEmployeeCode());

		dto.setFirstName(faculty.getFirstName());

		dto.setLastName(faculty.getLastName());

		dto.setFullName(faculty.getFirstName() + " " + faculty.getLastName());

		dto.setPhoneNumber(faculty.getPhoneNumber());

		dto.setGender(faculty.getGender());

		dto.setDateOfBirth(faculty.getDateOfBirth());

		dto.setJoiningDate(faculty.getJoiningDate());

		dto.setDesignation(faculty.getDesignation());

		dto.setQualification(faculty.getQualification());

		dto.setSalary(faculty.getSalary());

		dto.setProfileImage(faculty.getProfileImage());

		dto.setStatus(faculty.getStatus());

		// Department

		setDepartmentInformation(dto, faculty.getDepartment());

		// Address

		setAddressInformation(dto, faculty.getAddress());

		return dto;

	}

	// ==========================================================
	// ADMIN PROFILE
	// ==========================================================

	public ProfileResponseDto convertAdminToDto(AdminProfile admin) {

	    ProfileResponseDto dto = new ProfileResponseDto();

	    // User Information
	    User user = admin.getUser();
	    setUserInformation(dto, user);

	    // Admin Information
	    dto.setAdminId(admin.getId());

	    dto.setAdminName(
	            admin.getFirstName() + " " + admin.getLastName()
	    );

	    dto.setAdminDesignation(
	            admin.getDesignation()
	    );

	    return dto;
	}

	// ==========================================================
	// COMMON USER MAPPING
	// ==========================================================

	private void setUserInformation(ProfileResponseDto dto, User user) {

		if (user != null) {

			dto.setUserId(user.getId());

			dto.setEmail(user.getEmail());

			dto.setRole(user.getRole());

		}

	}

	// ==========================================================
	// COMMON DEPARTMENT MAPPING
	// ==========================================================

	private void setDepartmentInformation(ProfileResponseDto dto, Department department) {

		if (department != null) {

			dto.setDepartmentId(department.getId());

			dto.setDepartmentName(department.getDepartmentName());

		}

	}

	// ==========================================================
	// COMMON ADDRESS MAPPING
	// ==========================================================

	private void setAddressInformation(ProfileResponseDto dto, Address address) {

		if (address == null)
			return;

		dto.setAddressId(address.getId());

		dto.setHouseNumber(address.getHouseNumber());

		dto.setStreet(address.getStreet());

		dto.setLandmark(address.getLandmark());

		dto.setAddressType(address.getAddressType());

		Village village = address.getVillage();

		if (village != null) {

			dto.setVillageName(village.getVillageName());

			dto.setPincode(village.getPincode());

			Tehsil tehsil = village.getTehsil();

			if (tehsil != null) {

				dto.setTehsilName(tehsil.getTehsilName());

				District district = tehsil.getDistrict();

				if (district != null) {

					dto.setDistrictName(district.getDistrictName());

					State state = district.getState();

					if (state != null) {

						dto.setStateName(state.getStateName());

						Country country = state.getCountry();

						if (country != null) {

							dto.setCountryName(country.getCountryName());

						}

					}

				}

			}

		}

	}

}