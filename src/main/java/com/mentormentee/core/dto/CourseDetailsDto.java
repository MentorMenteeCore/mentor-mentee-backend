package com.mentormentee.core.dto;

import lombok.Data;

@Data
public class CourseDetailsDto {
    private Long id;
    private String courseName;
    private int credit;
    private String grade;
    private String professor;

    public CourseDetailsDto(Long id, String courseName, int credit, String grade, String professor) {
        this.id = id;
        this.courseName = courseName;
        this.credit = credit;
        this.grade = grade;
        this.professor = professor;
    }
}
