package com.mentormentee.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mentormentee.core.domain.Role;
import lombok.Getter;
import java.util.List;



@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MentorDetailsDto {
    private List<CourseDetailsDto> courseDetails;
    private List<AvailableTimeDto> availabilities;
    private String waysOfCommunication;
    private String selfIntroduction;
    private List<ReviewDto> reviews;
    private Role userRole;



    // 페이지 정보를 포함하는 필드
    private int totalPages;
    private int currentPageNum;
    private boolean lastPageOrNot;

    public MentorDetailsDto(List<CourseDetailsDto> courseDetails,
                            List<AvailableTimeDto> availabilities,
                            String waysOfCommunication,
                            String selfIntroduction,
                            List<ReviewDto> reviews,
                            Role userRole,
                            int totalPages,
                            int currentPageNum,
                            boolean lastPageOrNot
                            ){
        this.courseDetails = courseDetails;
        this.availabilities = availabilities;
        this.waysOfCommunication = waysOfCommunication;
        this.selfIntroduction = selfIntroduction;
        this.reviews = reviews;
        this.userRole = userRole;
        this.totalPages = totalPages;
        this.currentPageNum = currentPageNum;
        this.lastPageOrNot = lastPageOrNot;
    }
}



