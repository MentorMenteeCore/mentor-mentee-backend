package com.mentormentee.core.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserForm {

    @NotEmpty(message = "성명은 필수 기입하여야 합니다.")
    private String name;
    @NotEmpty(message = "이메일은 필수 기입하여야 합니다.")
    private String email;
    @NotEmpty(message = "인증번호는 필수 기입하여야 합니다.")
    private int verificationNumber;

}
