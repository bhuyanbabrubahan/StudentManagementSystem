package com.sms.security.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.payload.ApiResponseDto;
import com.sms.security.dto.ChangePasswordRequestDto;
import com.sms.security.dto.ChangePasswordResponseDto;
import com.sms.security.dto.ProfileResponseDto;
import com.sms.security.service.ProfileService;
import com.sms.security.util.JwtTokenUtil;
import com.sms.util.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(
        name = "Profile API",
        description = "APIs for logged-in user profile management"
)
public class ProfileController {

	private final ProfileService profileService;
	
	private static final Logger log =
	        LoggerFactory.getLogger(ProfileController.class);
	
	private final JwtTokenUtil jwtTokenUtil;

    // =====================================
    // GET PROFILE
    // =====================================

    @GetMapping("/me")
    @Operation(
            summary = "Get My Profile",
            description = "Fetch currently authenticated user profile details"
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "200",
                    description = "Profile fetched successfully"
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized user"
            )

    })
    public ResponseEntity<ApiResponseDto<ProfileResponseDto>> getMyProfile() {


        ProfileResponseDto response =
                profileService.getMyProfile();


        return ResponseBuilder.success(

                response,

                "Profile fetched successfully",

                HttpStatus.OK
        );

    }



 // =====================================
 // CHANGE PASSWORD
 // =====================================

 @PutMapping("/change-password")
 @Operation(
         summary = "Change Password",
         description =
         "Changes password of currently authenticated user after validating current password. Existing sessions are invalidated."
 )
 @ApiResponses({

         @ApiResponse(
                 responseCode = "200",
                 description = "Password changed successfully"
         ),

         @ApiResponse(
                 responseCode = "400",
                 description = "Invalid password request"
         ),

         @ApiResponse(
                 responseCode = "401",
                 description = "Current password incorrect or token expired"
         ),

         @ApiResponse(
                 responseCode = "404",
                 description = "User not found"
         )

 })
 public ResponseEntity<ApiResponseDto<ChangePasswordResponseDto>> changePassword(

         @Valid
         @RequestBody
         ChangePasswordRequestDto request,

         HttpServletRequest httpRequest

 ) {



     log.info(
             "Change password request received"
     );



     String token =
             jwtTokenUtil
             .getTokenFromRequest(httpRequest);



     ChangePasswordResponseDto response =

             profileService.changePassword(
                     request,
                     token
             );



     return ResponseBuilder.success(

             response,

             "Password changed successfully.",

             HttpStatus.OK

     );

 }


    

}