package com.mentormentee.core.token.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class
RefreshDto {
    @Schema(description = "refresh token")
    private String refreshToken;
}
