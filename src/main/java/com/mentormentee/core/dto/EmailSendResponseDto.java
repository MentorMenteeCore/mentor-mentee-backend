package com.mentormentee.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class EmailSendResponseDto {
    private String email;
    private String code;
    private LocalDateTime createdAt;
}
