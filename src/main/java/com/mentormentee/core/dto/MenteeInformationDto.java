package com.mentormentee.core.dto;

import com.mentormentee.core.repository.CourseNameAndMajorOnly;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

/**
 * 멘티의 정보를 담는 Dto입니다.
 * Dto를 사용해서 Api 스펙이 쉽게 바뀌지 않게 하기 위함입니다.
 */
@Data
public class MenteeInformationDto {

    private int totalPages;
    private int currentPageNum;
    private boolean lastPageOrNot;
    private String menteeNickName;
    private String menteeImageUrl;
    private String selfIntroduction;
    private List<CourseNameAndMajorOnly> userCourseList;
    private List<String> menteePreferredTeachingMethodDtoList;

    public MenteeInformationDto(int totalPages, int number, boolean last, String menteeNickName, String menteeImageUrl, String selfIntroduction, List<CourseNameAndMajorOnly> userCourseList, List<String> menteePreferredTeachingMethodDtoList) {
        this.totalPages = totalPages;
        this.currentPageNum = number;
        this.lastPageOrNot = last;
        this.menteeNickName = menteeNickName;
        this.menteeImageUrl = menteeImageUrl;
        this.selfIntroduction = selfIntroduction;
        this.userCourseList = userCourseList;
        this.menteePreferredTeachingMethodDtoList = menteePreferredTeachingMethodDtoList;
    }
}

