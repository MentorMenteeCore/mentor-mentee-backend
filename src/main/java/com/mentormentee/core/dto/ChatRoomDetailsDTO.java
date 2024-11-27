package com.mentormentee.core.dto;

import java.time.LocalDateTime;

public interface ChatRoomDetailsDTO {

    String getRoomId();

    String getLastMessageContent();

    LocalDateTime getLastMessageTime();

    String getOtherUserNickname();

    String getOtherUserProfilePicture();

    Long getOtherUserId();

    Long getUnreadCount();
}
