package com.mentormentee.core.dto;

import lombok.Data;

@Data
public class UserEmailDto {

    private String email;

    public UserEmailDto(String email) {
        this.email = email;
    }

    public UserEmailDto() {
    }
}
