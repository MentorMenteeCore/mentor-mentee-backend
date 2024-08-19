package com.mentormentee.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MentorDetailsDto {
    private String courseName;
    private int credits;
    private boolean isMajor;
    private String gradeStatus;
    private LocalTime availableStartTime;
    private LocalTime availableEndTime;
    private String waysOfCommunication;
    private String selfIntroduction;
    private List<ReviewDto> reviews;


    public MentorDetailsDto(String courseName, int credits, boolean isMajor, String gradeStatus,
                            LocalTime availableStartTime, LocalTime availableEndTime, String waysOfCommunication, String selfIntroduction,
                            List<ReviewDto> reviews) {
        this.courseName = courseName;
        this.credits = credits;
        this.isMajor = isMajor;
        this.gradeStatus = gradeStatus;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.waysOfCommunication = waysOfCommunication;
        this.selfIntroduction = selfIntroduction;
        this.reviews = reviews;
    }
}

