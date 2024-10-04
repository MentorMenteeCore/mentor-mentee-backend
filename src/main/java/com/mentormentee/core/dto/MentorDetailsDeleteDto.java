package com.mentormentee.core.dto;

import lombok.Getter;
import java.util.List;


@Getter
public class MentorDetailsDeleteDto {
    private List<Long> availabilityId;  //availabilityId를 사용하여 삭제
    private List<Long> courseDetailsId;  //usercourseId를 사용하여 삭제 
}


