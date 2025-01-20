package com.mentormentee.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageSaveDto {

    public String message;
    public String roomId;
    public LocalDateTime now;
    public boolean isUserInRoom;
    public Long senderId;

}
