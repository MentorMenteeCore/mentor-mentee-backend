package com.mentormentee.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mentormentee.core.domain.Course;
import com.mentormentee.core.domain.GradeStatus;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserCourse;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
public class CourseMentorDto {
    private String courseName;
    private List<MentorDto> mentors;


    public CourseMentorDto(String courseName, List<MentorDto> mentors, int totalMentors) {
        this.courseName = courseName;
        this.mentors = mentors;
    }

    @Getter
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public static class MentorDto {
        private Long userId;  // userId 추가
        private String nickName;
        private String courseName;
        private String gradeStatus;
        private int yearInUni;
        private int cieatStock;
        private int cieatGrade;

        public MentorDto(User user, Course course, UserCourse userCourse, int cieatStock, int cieatGrade) {
            this.userId = user.getId();
            this.nickName = user.getNickName();
            this.courseName = course.getCourseName();
            this.gradeStatus = userCourse.getGradeStatus().getDisplayValue();
            this.yearInUni = user.getYearInUni();
            this.cieatStock = cieatStock;
            this.cieatGrade = cieatGrade;
        }

        @JsonIgnore
        public int getGradeStatusPriority() {
            return GradeStatus.fromString(this.gradeStatus).getPriority();
        }
    }
}









