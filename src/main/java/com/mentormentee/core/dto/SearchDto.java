package com.mentormentee.core.dto;

public class SearchDto {
    public String imageUrl;
    public String departmentName;
    public String collegeName;

    public SearchDto(String imageUrl, String departmentName, String collegeName) {
        this.imageUrl = imageUrl;
        this.departmentName = departmentName;
        this.collegeName = collegeName;
    }
}
