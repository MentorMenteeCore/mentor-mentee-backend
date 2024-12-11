package com.mentormentee.core.dto;

import com.mentormentee.core.domain.Department;
import com.mentormentee.core.domain.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class AllMentorListDto {
    private List<MentorDto> mentorDtos;
    private int totalPages;
    private int currentPageNum;
    private boolean lastPageOrNot;

    // 멘토 리스트와 페이지 정보 받는 생성자
    public AllMentorListDto(List<MentorDto> mentorDtos, int totalPages, int currentPageNum, boolean lastPageOrNot) {
        this.mentorDtos = mentorDtos;
        this.totalPages = totalPages;
        this.currentPageNum = currentPageNum;
        this.lastPageOrNot = lastPageOrNot;
    }

    @Getter
    public static class MentorDto {
        private String nickName;
        private int yearInUni;
        private String departmentName;
        private String profileUrl;

        //@QueryProjection 어노테이션 추가
        @QueryProjection
        public MentorDto(User user, Department department) {
            this.nickName = user.getNickName();
            this.yearInUni = user.getYearInUni();
            // department가 null이면 기본값 설정
            this.departmentName = (department != null && department.getId() != null)
                    ? department.getDepartmentName()
                    : null;  // department_id가 null이면 departmentName을 null로 설정

            this.profileUrl = user.getUserProfilePicture();
        }
    }
}
