package com.sms.security.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.payload.ApiResponseDto;
import com.sms.security.dto.ChangePasswordRequestDto;
import com.sms.security.dto.ChangePasswordResponseDto;
import com.sms.security.dto.ProfileResponseDto;
import com.sms.security.service.ProfileService;
import com.sms.util.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/profile")
@Tag(
        name = "Profile API",
        description = "APIs for logged-in user profile"
)
public class ProfileController {


    private final ProfileService profileService;


    @GetMapping("/me")
    @Operation(
            summary = "Get My Profile",
            description = "Returns profile details of the currently logged-in user"
    )
    public ResponseEntity<ApiResponseDto<ProfileResponseDto>> getMyProfile() {

        ProfileResponseDto response = profileService.getMyProfile();

        return ResponseBuilder.success(
                response,
                "Profile fetched successfully",
                HttpStatus.OK
        );
    }
    
    
    
	// CHANGE PASSWORD

    @Operation(
            summary = "Change Password",
            description = "Allows the logged-in user to change their password after verifying the current password."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Current password is incorrect"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponseDto<ChangePasswordResponseDto>> changePassword(

            @Valid
            @RequestBody
            ChangePasswordRequestDto request) {


        ChangePasswordResponseDto response =
                profileService.changePassword(request);



        return ResponseBuilder.success(
                response,
                "Password changed successfully.",
                HttpStatus.OK
        );
    }
    
    
    


}