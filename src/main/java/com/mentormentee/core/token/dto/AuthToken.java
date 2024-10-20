package com.mentormentee.core.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class AuthToken {
    private String grantType;//access 토큰 타입 : Bearer로 고정
    private String accessToken;
    private String refreshToken;
}