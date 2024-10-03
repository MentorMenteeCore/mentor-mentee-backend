package com.mentormentee.core.dto;

import com.mentormentee.core.repository.CourseNameOnly;
import com.mentormentee.core.repository.PreferredTeachingMethodOnly;
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
    private String menteeName;
    private String menteeProfileUrl;
    private String selfIntroduction;
    private List<CourseNameOnly> userCourseList;
    private List<PreferredTeachingMethodOnly> menteePreferredTeachingMethodDtoList;

    public MenteeInformationDto(int totalPages, int number, boolean last, String menteeName, String menteeProfileUrl, String selfIntroduction, List<CourseNameOnly> userCourseList, List<PreferredTeachingMethodOnly> menteePreferredTeachingMethodDtoList) {
        this.totalPages = totalPages;
        this.currentPageNum = number;
        this.lastPageOrNot = last;
        this.menteeName = menteeName;
        this.menteeProfileUrl = menteeProfileUrl;
        this.selfIntroduction = selfIntroduction;
        this.userCourseList = userCourseList;
        this.menteePreferredTeachingMethodDtoList = menteePreferredTeachingMethodDtoList;
    }
}

