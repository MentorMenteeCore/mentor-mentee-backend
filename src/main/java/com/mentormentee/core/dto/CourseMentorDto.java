package com.mentormentee.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mentormentee.core.domain.Course;
import com.mentormentee.core.domain.GradeStatus;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserCourse;
import lombok.Getter;
import java.util.List;


@Getter
public class CourseMentorDto {
    private String courseName;
    private List<MentorDto> mentors;
    private int totalMentors;
    private List<CourseDto> courseDtoList;
    private int totalPages;
    private int currentPageNum;
    private boolean lastPageOrNot;

    public CourseMentorDto(String courseName, List<MentorDto> mentors, int totalMentors, List<CourseDto> courseDtoList, int totalPages, int currentPageNum, boolean lastPageOrNot) {
        this.courseName = courseName;
        this.mentors = mentors;
        this.totalMentors = totalMentors;
        this.courseDtoList = courseDtoList;
        this.totalPages = totalPages;
        this.currentPageNum = currentPageNum;
        this.lastPageOrNot = lastPageOrNot;
    }

    @Getter
    public static class CourseDto {
        private Long courseId;
        private String courseName;

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

        public MentorDto(User user, Course course, UserCourse userCourse) {
            this.nickName = user.getNickName();
            this.courseName = course.getCourseName();
            this.gradeStatus = userCourse.getGradeStatus().getDisplayValue();
            this.yearInUni = user.getYearInUni();
        }

        @JsonIgnore
        public int getGradeStatusPriority() {
            return GradeStatus.fromString(this.gradeStatus).getPriority();
        }
    }
}












