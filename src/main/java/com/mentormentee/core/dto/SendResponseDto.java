package com.mentormentee.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SendResponseDto {

    private String roodId;
    private String senderId;       // 메시지를 보낸 사람
    private LocalDateTime currentTime;
    private boolean isOtherUserJoined;
    private String senderProfilePictureUrl;
    private String message;

}
