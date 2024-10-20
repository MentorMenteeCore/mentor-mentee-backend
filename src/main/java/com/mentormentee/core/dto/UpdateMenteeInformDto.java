package com.mentormentee.core.dto;


import lombok.Data;

import java.util.List;

@Data
public class UpdateMenteeInformDto {

    private String menteeImageUrl;
    private String selfIntroduction;
    private List<CourseNameDto> userCourseList;
    private List<PreferredTeachingMethodDto> menteePreferredTeachingMethodDtoList;

}
