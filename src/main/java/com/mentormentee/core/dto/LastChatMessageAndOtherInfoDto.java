package com.mentormentee.core.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LastChatMessageAndOtherInfoDto {

    private String roomId;
    private Long unreadCount;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private String otherPictureUrl;
    private String otherNickName;

}
