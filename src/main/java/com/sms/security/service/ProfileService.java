package com.sms.security.service;

import com.sms.security.dto.ChangePasswordRequestDto;
import com.sms.security.dto.ChangePasswordResponseDto;
import com.sms.security.dto.ProfileResponseDto;

public interface ProfileService {


    ProfileResponseDto getMyProfile();

    ChangePasswordResponseDto changePassword(
            ChangePasswordRequestDto request,
            String token);


}