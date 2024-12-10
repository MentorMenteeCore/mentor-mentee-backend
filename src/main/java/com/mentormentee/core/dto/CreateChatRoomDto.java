package com.mentormentee.core.dto;

import lombok.Data;

@Data
public class CreateChatRoomDto {

    private String userID;
    private String otherID;

    public CreateChatRoomDto(String userID, String otherID) {
        this.userID = userID;
        this.otherID = otherID;
    }
}
