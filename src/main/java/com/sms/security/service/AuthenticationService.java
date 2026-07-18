package com.sms.security.service;

import com.sms.security.dto.ForgotPasswordRequestDto;
import com.sms.security.dto.LoginRequestDto;
import com.sms.security.dto.LoginResponseDto;
import com.sms.security.dto.LogoutResponseDto;
import com.sms.security.dto.PasswordResetResponseDto;
import com.sms.security.dto.RefreshTokenRequestDto;
import com.sms.security.dto.RefreshTokenResponseDto;
import com.sms.security.dto.RegisterRequestDto;
import com.sms.security.dto.RegisterResponseDto;
import com.sms.security.dto.ResetPasswordRequestDto;

public interface AuthenticationService {

	RegisterResponseDto register(RegisterRequestDto request);

	LoginResponseDto login(LoginRequestDto request);

	PasswordResetResponseDto forgotPassword(ForgotPasswordRequestDto request);

	PasswordResetResponseDto resetPassword(ResetPasswordRequestDto request);

	LogoutResponseDto logout();

	RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request);
}