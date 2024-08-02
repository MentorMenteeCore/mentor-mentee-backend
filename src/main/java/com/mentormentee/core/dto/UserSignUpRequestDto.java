package com.mentormentee.core.dto;

import com.mentormentee.core.domain.Role;
import lombok.Getter;

@Getter
public class UserSignUpRequestDto {
    private String email;
    private String password;
    private String userName;
    private String nickname;
    private Role role;
}
