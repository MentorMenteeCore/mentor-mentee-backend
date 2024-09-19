package com.mentormentee.core.utils;

import com.mentormentee.core.comparator.OrderingByKoreanEnglishNumbuerSpecial;
import com.mentormentee.core.dto.CourseMentorDto;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;


//멘토 목록 정렬
import java.util.Comparator;

public class MentorComparator implements Comparator<CourseMentorDto.MentorDto> {
    private final String sortBy;
    private final Comparator<String> nicknameComparator;

    public MentorComparator(String sortBy) {
        this.sortBy = sortBy;
        this.nicknameComparator = OrderingByKoreanEnglishNumbuerSpecial.getComparator();
    }

    @Override
    public int compare(CourseMentorDto.MentorDto m1, CourseMentorDto.MentorDto m2) {
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

    private int compareByNickname(CourseMentorDto.MentorDto m1, CourseMentorDto.MentorDto m2) {
        return nicknameComparator.compare(m1.getNickName(), m2.getNickName());
    }

    private int compareByGradeStatus(CourseMentorDto.MentorDto m1, CourseMentorDto.MentorDto m2) {
        return Integer.compare(m1.getGradeStatusPriority(), m2.getGradeStatusPriority());
    }

    private int compareByYearInUni(CourseMentorDto.MentorDto m1, CourseMentorDto.MentorDto m2) {
        return Integer.compare(m1.getYearInUni(), m2.getYearInUni());
    }
}











