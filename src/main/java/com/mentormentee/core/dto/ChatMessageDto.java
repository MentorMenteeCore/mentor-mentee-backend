package com.mentormentee.core.dto;

import lombok.Data;

@Data
public class ChatMessageDto {

    private String roomId;
    private String senderId;       // 메시지를 보낸 사람
    private String message;      // 메시지 내용


    public static Long getOtherId(ChatMessageDto chatMessageDto) {
        String roomId = chatMessageDto.getRoomId();
        String[] splitRoomId = roomId.split("/");
        if(splitRoomId[0].equals(chatMessageDto.getSenderId())) {
            return Long.valueOf(splitRoomId[1]);
        }else {
            return Long.valueOf(splitRoomId[0]);
        }
    }
}
