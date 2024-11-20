package com.mentormentee.core.dto;

import lombok.Data;

@Data
public class CourseDetailsDto {
    private String courseName;
    private int credit;
    private String gradeStatus;

    public CourseDetailsDto( String courseName, int credit, String gradeStatus) {
        this.courseName = courseName;
        this.credit = credit;
        this.gradeStatus = gradeStatus;
    }
}
