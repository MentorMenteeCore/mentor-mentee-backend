package com.mentormentee.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public enum CourseYear {
    FRESHMAN(1),
    SOPHOMORE(2),
    JUNIOR(3),
    SENIOR(4);

    private final int year;

    CourseYear(int year) {
        this.year = year;
    }

    
    //int형 변환
    public static CourseYear fromInt(int yearInUni) {
        for (CourseYear courseYear : values()) {
            if (courseYear.getYear() == yearInUni) {
                return courseYear;
            }
        }
        throw new IllegalArgumentException("잘못된 학년입니다: " + yearInUni);
    }

    public String toLowerCaseString() {
        return this.name().toLowerCase();
    }

    
    //문자열 변환
    public static CourseYear fromString(String yearString) {
        for (CourseYear courseYear : values()) {
            if (courseYear.toLowerCaseString().equals(yearString.toLowerCase())) {
                return courseYear;
            }
        }
        throw new IllegalArgumentException("잘못된 입력입니다: " + yearString);
    }
}





































