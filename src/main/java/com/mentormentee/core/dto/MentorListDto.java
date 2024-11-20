package com.mentormentee.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mentormentee.core.domain.*;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class MentorListDto {
    private String courseName;
    private List<MentorDto> mentors;
    private List<CourseDto> courseDtoList;
    private int totalPages;
    private int currentPageNum;
    private boolean lastPageOrNot;
    private int userYearInUni;

    public MentorListDto(String courseName, List<MentorDto> mentors, List<CourseDto> courseDtoList, int totalPages, int currentPageNum, boolean lastPageOrNot, int userYearInUni) {
        this.courseName = courseName;
        this.mentors = mentors;
        this.courseDtoList = courseDtoList;
        this.totalPages = totalPages;
        this.currentPageNum = currentPageNum;
        this.lastPageOrNot = lastPageOrNot;
        this.userYearInUni = userYearInUni;
    }

    @Getter
    public static class CourseDto {
        private Long courseId;
        private String courseName;

        //@QueryProjection 어노테이션 추가(Qclass로 만들기)
        @QueryProjection
        public CourseDto(Course course) {
            this.courseId = course.getId();
            this.courseName = course.getCourseName();
        }
    }

    @Getter
    public static class MentorDto {
        private String nickName;
        private String courseName;
        private String gradeStatus;
        private int yearInUni;
        private String departmentName;
        private String profileUrl;

        //@QueryProjection 어노테이션 추가
        @QueryProjection
        public MentorDto(User user, Course course, UserCourse userCourse, Department department) {
            this.nickName = user.getNickName();
            this.courseName = course.getCourseName();
            this.gradeStatus = userCourse.getGradeStatus().getDisplayValue();
            this.yearInUni = user.getYearInUni();
            this.departmentName = department.getDepartmentName();
            this.profileUrl = user.getUserProfilePicture();
        }

        @JsonIgnore
        public int getGradeStatusPriority() {
            return GradeStatus.fromString(this.gradeStatus).getPriority();
        }
    }
}












