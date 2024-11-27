package com.mentormentee.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessagesDto {

    List<ChatRoomDetailsDTO> chatRooms;
    List<MessageDetailsDTO> chatMessages;
}
