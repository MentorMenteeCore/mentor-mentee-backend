package com.mentormentee.core.dto;

import com.mentormentee.core.domain.Department;
import jakarta.validation.constraints.Email;
import lombok.Data;

/**
 * 유저 정보 업데이트 Form 입니다.
 * Email은 양식을 지키지 않을시
 * 400 오류
 */
@Data
public class UserInformDto {

    private String userNickname;

    @Email
    private String userEmail;

    private String userDepartment;

    private int yearInUni;

    private String profileUrl;

    public UserInformDto(String userNickname, String userEmail, String userDepartment, int yearInUni, String profileUrl) {
        this.userNickname = userNickname;
        if(userEmail != null && !userEmail.isEmpty()){
            this.userEmail = userEmail;
        }
        this.userDepartment = userDepartment;
        this.yearInUni = yearInUni;
        this.profileUrl = profileUrl;
    }
}

