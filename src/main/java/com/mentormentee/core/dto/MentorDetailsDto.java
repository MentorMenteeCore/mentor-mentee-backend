package com.mentormentee.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class MentorDetailsDto {
    private List<CourseDetailsDto> courseDetails;
    private List<AvailableTimeDto> availabilities;
    private String waysOfCommunication;
    private String selfIntroduction;
    private String nickName;
    private String userProfilePictureUrl;
    private List<ReviewDto> reviews;
    private int reviewSize;
    private int totalPages;
    private int currentPageNum;
    private boolean lastPageOrNot;

    public MentorDetailsDto(List<CourseDetailsDto> courseDetails,
                            List<AvailableTimeDto> availabilities,
                            String waysOfCommunication,
                            String selfIntroduction,
                            List<ReviewDto> reviews,
                            int totalPages,
                            int currentPageNum,
                            boolean lastPageOrNot,
                            String nickName, String userProfilePicture, int size) {
        this.courseDetails = courseDetails;
        this.availabilities = availabilities;
        this.waysOfCommunication = waysOfCommunication;
        this.selfIntroduction = selfIntroduction;
        this.reviews = reviews;
        this.totalPages = totalPages;
        this.currentPageNum = currentPageNum;
        this.lastPageOrNot = lastPageOrNot;
        this.nickName = nickName;
        this.userProfilePictureUrl = userProfilePicture;
        this.reviewSize = size;
    }

    public boolean getLastPageOrNot() {
        return lastPageOrNot;
    }
}

