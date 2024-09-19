package com.mentormentee.core.dto;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MentorDetailsUpdateDto {
    private List<CourseDetailsDto> courseDetails = new ArrayList<>();
    private List<AvailableTimeDto> availabilities = new ArrayList<>();
    private String waysOfCommunication;
    private String selfIntroduction;

}


