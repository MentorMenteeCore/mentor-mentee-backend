package com.mentormentee.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailsDto {
    private Long id;
    private String courseName;
    private int credit;
    private String gradeStatus;
    private String professor;
}

