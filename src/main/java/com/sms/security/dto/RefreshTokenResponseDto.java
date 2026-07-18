package com.sms.security.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "RefreshTokenResponse",
        description = "Response returned after generating a new access token using a valid refresh token."
)
public class RefreshTokenResponseDto {

    @Schema(
            description = "New JWT Access Token",
            example = "eyJhbGciOiJIUzI1NiJ9..."
    )
    private String accessToken;

    @Schema(
            description = "Token type",
            example = "Bearer"
    )
    @Builder.Default
    private String tokenType = "Bearer";

    @Schema(
            description = "Access token expiry time in seconds",
            example = "900"
    )
    private Long expiresIn;

    @Schema(
            description = "Response timestamp"
    )
    private LocalDateTime timestamp;
}