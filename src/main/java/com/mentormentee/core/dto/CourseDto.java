package com.mentormentee.core.dto;

import lombok.Data;

@Data
public class CourseDto {
    private String courseName;
    private int credit;
    private String gradeStatus;

    public CourseDto( String courseName, int credit, String grade) {
        this.courseName = courseName;
        this.credit = credit;
        this.gradeStatus = grade;
    }
}
