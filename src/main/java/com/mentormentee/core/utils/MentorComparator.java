package com.mentormentee.core.utils;

import com.mentormentee.core.comparator.OrderingByKoreanEnglishNumbuerSpecial;
import com.mentormentee.core.dto.MentorListDto;

import java.util.Comparator;


//멘토 목록 정렬


public class MentorComparator implements Comparator<MentorListDto.MentorDto> {
    private final String sortBy;
    private final Comparator<String> nicknameComparator;

    public MentorComparator(String sortBy) {
        this.sortBy = sortBy;
        this.nicknameComparator = OrderingByKoreanEnglishNumbuerSpecial.getComparator();
    }


    @Override
    public int compare(MentorListDto.MentorDto m1, MentorListDto.MentorDto m2) {
        switch (sortBy) {
            case "nickname":
                return compareByNickname(m1, m2);
            case "gradestatus":
                return compareByGradeStatus(m1, m2);
            case "yearinuni":
                return compareByYearInUni(m1, m2);
            default:
                throw new IllegalArgumentException("Invalid sort criterion: " + sortBy);
        }
    }



    private int compareByNickname(MentorListDto.MentorDto m1, MentorListDto.MentorDto m2) {
        return nicknameComparator.compare(m1.getNickName(), m2.getNickName());
    }

    private int compareByGradeStatus(MentorListDto.MentorDto m1, MentorListDto.MentorDto m2) {
        return Integer.compare(m1.getGradeStatusPriority(), m2.getGradeStatusPriority());
    }

    private int compareByYearInUni(MentorListDto.MentorDto m1, MentorListDto.MentorDto m2) {
        return Integer.compare(m1.getYearInUni(), m2.getYearInUni());
    }
}









