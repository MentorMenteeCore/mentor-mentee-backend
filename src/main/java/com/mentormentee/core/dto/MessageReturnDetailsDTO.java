package com.mentormentee.core.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MessageReturnDetailsDTO {

    private Long messageId;
    private String content;
    private LocalDateTime time;
    private Boolean readOrNot;
    private String senderNickname;
    private String senderProfilePicture;
    private Boolean isCurrentUser;

    public MessageReturnDetailsDTO(Long messageId, String content, LocalDateTime time, Integer readOrNot, String senderNickname, String senderProfilePicture, Integer isCurrentUser) {
        this.messageId = messageId;
        this.content = content;
        this.time = time;
        if(readOrNot.equals(1)){
            this.readOrNot = true;
        }else {
            this.readOrNot = false;
        }
        this.senderNickname = senderNickname;
        this.senderProfilePicture = senderProfilePicture;
        if(isCurrentUser.equals(1)){
            this.isCurrentUser = true;
        }else {
            this.isCurrentUser = false;
        }
    }
}
