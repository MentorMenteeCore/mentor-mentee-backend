package com.mentormentee.core.dto;

import com.mentormentee.core.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OtherChatRoomsDto {
    private String roomId;
    private Long unreadCount;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private User opponent;
}
