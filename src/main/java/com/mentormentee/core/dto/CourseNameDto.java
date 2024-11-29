package com.mentormentee.core.dto;

import com.mentormentee.core.domain.GradeStatus;
import com.mentormentee.core.domain.IsMajor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CourseNameDto {

    private String courseName;

    private String grade;

    private String department;

    public CourseNameDto(String courseName, String grade, String department) {
        this.courseName = courseName;
        this.grade = grade;
        this.department = department;
    }
}
