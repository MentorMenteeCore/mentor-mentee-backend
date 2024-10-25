package com.mentormentee.core.dto;

import lombok.Data;

@Data
public class ChatMessageDto {

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    private MessageType type;    // 메시지 타입 (채팅, 입장, 퇴장)
    private int menteeId;
    private String sender;       // 메시지를 보낸 사람
    private String message;      // 메시지 내용
    private int mentorId;


}
