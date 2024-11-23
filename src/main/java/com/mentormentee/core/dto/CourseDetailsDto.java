package com.mentormentee.core.dto;

import lombok.Data;

@Data
public class CourseDetailsDto {
    private Long id;
    private String courseName;
    private int credit;
    private String gradeStatus;

    public CourseDetailsDto( Long id,String courseName, int credit, String gradeStatus) {
        this.id = id;
        this.courseName = courseName;
        this.credit = credit;
        this.gradeStatus = gradeStatus;
    }
}
