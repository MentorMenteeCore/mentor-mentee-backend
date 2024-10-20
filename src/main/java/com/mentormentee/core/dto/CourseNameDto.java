package com.mentormentee.core.dto;

import com.mentormentee.core.domain.IsMajor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CourseNameDto {

    private String courseName;

    private IsMajor isMajor;

    public CourseNameDto(String courseName, IsMajor isMajor) {
        this.courseName = courseName;
        this.isMajor = isMajor;
    }
}
