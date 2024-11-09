package com.mentormentee.core.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.mentormentee.core.dto.QMentorListDto_CourseDto is a Querydsl Projection type for CourseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMentorListDto_CourseDto extends ConstructorExpression<MentorListDto.CourseDto> {

    private static final long serialVersionUID = 770306170L;

    public QMentorListDto_CourseDto(com.querydsl.core.types.Expression<? extends com.mentormentee.core.domain.Course> course) {
        super(MentorListDto.CourseDto.class, new Class<?>[]{com.mentormentee.core.domain.Course.class}, course);
    }

}

