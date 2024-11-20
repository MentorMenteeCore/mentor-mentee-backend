package com.mentormentee.core.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class AvailableTimeDto {
    private DayOfWeek dayOfWeek;
    private LocalTime availableStartTime;
    private LocalTime availableEndTime;

    public AvailableTimeDto(DayOfWeek dayOfWeek, LocalTime availableStartTime, LocalTime availableEndTime) {
        this.dayOfWeek = dayOfWeek;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
    }
}
