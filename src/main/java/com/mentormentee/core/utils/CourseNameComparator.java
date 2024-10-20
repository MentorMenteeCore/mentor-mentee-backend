package com.mentormentee.core.utils;

import com.mentormentee.core.comparator.OrderingByKoreanEnglishNumbuerSpecial;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.Collator;
import java.util.Locale;


import java.util.Comparator;
import java.util.Locale;
import java.text.Collator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseNameComparator implements Comparator<String> {
    private final Comparator<String> comparator;

    public CourseNameComparator() {
        // 한글 우선 정렬을 위한 Comparator를 사용합니다.
        comparator = OrderingByKoreanEnglishNumbuerSpecial.getComparator();
    }

    @Override
    public int compare(String o1, String o2) {
        // OrderingByKoreanEnglishNumbuerSpecial을 이용한 비교
        return comparator.compare(o1, o2);
    }
}



