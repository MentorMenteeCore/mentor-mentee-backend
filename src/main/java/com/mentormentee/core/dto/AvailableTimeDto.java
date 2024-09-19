package com.mentormentee.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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


