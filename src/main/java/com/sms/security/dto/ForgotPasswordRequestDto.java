package com.sms.security.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(
        name = "ForgotPasswordRequestDto",
        description = "Request DTO used to initiate password reset process."
)
public class ForgotPasswordRequestDto {


    @Schema(
            description = "Registered user email address",
            example = "amit.sharma@ums.edu.in",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

}