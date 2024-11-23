package com.mentormentee.core.dto;

public class SearchDto {
    public String departmentImageUrl;
    public String departmentName;
    public String collegeName;

    public SearchDto(String imageUrl, String departmentName, String collegeName) {
        this.departmentImageUrl = imageUrl;
        this.departmentName = departmentName;
        this.collegeName = collegeName;
    }
}
