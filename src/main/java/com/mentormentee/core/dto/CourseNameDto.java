package com.mentormentee.core.dto;

import com.mentormentee.core.domain.IsMajor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CourseNameDto {

    private String courseName;


    public CourseNameDto(String courseName) {
        this.courseName = courseName;

    }
}
