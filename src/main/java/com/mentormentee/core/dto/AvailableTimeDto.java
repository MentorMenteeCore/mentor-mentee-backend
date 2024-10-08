package com.mentormentee.core.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class AvailableTimeDto {
    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime availableStartTime;
    private LocalTime availableEndTime;

    public AvailableTimeDto(Long id, DayOfWeek dayOfWeek, LocalTime availableStartTime, LocalTime availableEndTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
    }
}
