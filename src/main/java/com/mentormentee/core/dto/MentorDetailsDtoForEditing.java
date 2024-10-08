package com.mentormentee.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class MentorDetailsDtoForEditing {

    private List<CourseDetailsDto> courseDetails;
    private List<AvailableTimeDto> availabilities;
    private String waysOfCommunication;
    private String selfIntroduction;
    private String nickName;
    private String userProfilePictureUrl;
    private int totalPages;
    private int currentPageNum;
    private boolean lastPageOrNot;

    public MentorDetailsDtoForEditing(List<CourseDetailsDto> courseDetails,
                            List<AvailableTimeDto> availabilities,
                            String waysOfCommunication,
                            String selfIntroduction,
                            int totalPages,
                            int currentPageNum,
                            boolean lastPageOrNot,
                            String nickName, String userProfilePicture) {
        this.courseDetails = courseDetails;
        this.availabilities = availabilities;
        this.waysOfCommunication = waysOfCommunication;
        this.selfIntroduction = selfIntroduction;
        this.totalPages = totalPages;
        this.currentPageNum = currentPageNum;
        this.lastPageOrNot = lastPageOrNot;
        this.nickName = nickName;
        this.userProfilePictureUrl = userProfilePicture;
    }
}
