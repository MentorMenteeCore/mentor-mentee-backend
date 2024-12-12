package com.mentormentee.core.dto;

import java.time.LocalDateTime;

public interface MessageDetailsDTO {

    Long getMessageId();

    String getContent();

    LocalDateTime getTime();

    Integer getReadOrNot();

    String getSenderNickname();

    String getSenderProfilePicture();

    Integer getIsCurrentUser();
}