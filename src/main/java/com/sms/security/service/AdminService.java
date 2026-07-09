package com.sms.security.service;

import com.sms.security.dto.AdminCreateRequestDto;
import com.sms.security.dto.AdminResponseDto;

public interface AdminService {

	AdminResponseDto createAdmin(AdminCreateRequestDto request);

}