package com.mentormentee.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordUpdateDto {

    @NotBlank(message = "기존 비밀번호를 입력해 주세요")
    @Size(min = 5, max = 15, message = "비밀번호는 5자리 이상 15자리 이하로 적어주세요")
    private String oldPassword;

    @NotBlank(message = "변경 할 비밀번호를 입력해 주세요")
    @Size(min = 5, max = 15, message = "비밀번호는 5자리 이상 15자리 이하로 적어주세요")
    private String newPassword;

    @NotBlank(message = "한번 더 비밀번호를 입력해 주세요")
    @Size(min = 5, max = 15, message = "비밀번호는 5자리 이상 15자리 이하로 적어주세요")
    private String confirmPassword;

}
