package com.mentormentee.core.controller.converter;

import com.mentormentee.core.domain.CollegeName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * String 타입 소문자 대문자로 바꿈
 */
@Component
public class StringToCollegeConverter implements Converter<String, CollegeName> {
    @Override
    public CollegeName convert(String source) {
        try {
            System.out.println(source);
            return CollegeName.valueOf(source.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 URL 경로 접속 시도: " + source);
        }
    }
}
