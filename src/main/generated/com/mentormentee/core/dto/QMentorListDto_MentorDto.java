package com.mentormentee.core.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.mentormentee.core.dto.QMentorListDto_MentorDto is a Querydsl Projection type for MentorDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMentorListDto_MentorDto extends ConstructorExpression<MentorListDto.MentorDto> {

    private static final long serialVersionUID = 2058756884L;

    public QMentorListDto_MentorDto(com.querydsl.core.types.Expression<? extends com.mentormentee.core.domain.User> user, com.querydsl.core.types.Expression<? extends com.mentormentee.core.domain.Course> course, com.querydsl.core.types.Expression<? extends com.mentormentee.core.domain.UserCourse> userCourse, com.querydsl.core.types.Expression<? extends com.mentormentee.core.domain.Department> department) {
        super(MentorListDto.MentorDto.class, new Class<?>[]{com.mentormentee.core.domain.User.class, com.mentormentee.core.domain.Course.class, com.mentormentee.core.domain.UserCourse.class, com.mentormentee.core.domain.Department.class}, user, course, userCourse, department);
    }

}

