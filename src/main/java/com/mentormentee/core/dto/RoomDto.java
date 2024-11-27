package com.mentormentee.core.dto;

import com.mentormentee.core.domain.ChatRoom;
import lombok.Data;

@Data
public class RoomDto {
    private Long userId;
    private Long otherId;
    private String RoomId;

    public RoomDto(Long userId, Long otherId, String roomId) {
        this.userId = userId;
        this.otherId = otherId;
        RoomId = roomId;
    }

    public static Long getUserIdFromStringId(String id){
        return Long.valueOf(id);
    }

    public static RoomDto createRoomDto(CreateChatRoomDto chatRoomDto) {

        String roomId = ChatRoom.getRoomId(chatRoomDto.getUserID(), chatRoomDto.getOtherID());
        Long currentUser = getUserIdFromStringId(chatRoomDto.getUserID());
        Long otherUser = getUserIdFromStringId(chatRoomDto.getOtherID());

        return new RoomDto(currentUser, otherUser, roomId);
    }
}
