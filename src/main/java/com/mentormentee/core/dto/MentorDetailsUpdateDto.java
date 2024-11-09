package com.mentormentee.core.dto;

import com.mentormentee.core.domain.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MentorDetailsUpdateDto {

    private final List<CourseDetailsDto> courseDetails;
    private final List<AvailableTimeDto> availabilities;
    private final String waysOfCommunication;
    private final String selfIntroduction;
    private final Role userRole;
    private final String profileUrl;
    private final int totalPages;
    private final int currentPageNum;
    private final boolean lastPageOrNot;

    // 생성자
    public MentorDetailsUpdateDto(List<CourseDetailsDto> courseDetails,
                                  List<AvailableTimeDto> availabilities,
                                  String waysOfCommunication,
                                  String selfIntroduction,
                                  Role userRole,
                                  String profileUrl,
                                  int totalPages,
                                  int currentPageNum,
                                  boolean lastPageOrNot) {
        this.courseDetails = (courseDetails != null) ? new ArrayList<>(courseDetails) : null; // null 허용
        this.availabilities = availabilities != null ? new ArrayList<>(availabilities) : new ArrayList<>();
        this.waysOfCommunication = waysOfCommunication;
        this.selfIntroduction = selfIntroduction;
        this.userRole = userRole;
        this.profileUrl = profileUrl;
        this.totalPages = totalPages;
        this.currentPageNum = currentPageNum;
        this.lastPageOrNot = lastPageOrNot;
    }

}
