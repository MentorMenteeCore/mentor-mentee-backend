package com.mentormentee.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLookAsideDto {

    private String userProfilePicture;
    private String userCurrentAccessedChatRoom;

}
