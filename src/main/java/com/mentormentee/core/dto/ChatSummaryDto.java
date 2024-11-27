package com.mentormentee.core.dto;

import com.mentormentee.core.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatSummaryDto {
    private String roomId;
    private Long unreadCount;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private User opponent;

    public ChatSummaryDto(String roomId, Long unreadCount, String lastMessage, LocalDateTime lastMessageTime, User opponent) {
        this.roomId = roomId;
        this.unreadCount = unreadCount;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
        this.opponent = opponent;
    }

}
