package com.mentormentee.core.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.mentormentee.core.dto.QAllMentorListDto_MentorDto is a Querydsl Projection type for MentorDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAllMentorListDto_MentorDto extends ConstructorExpression<AllMentorListDto.MentorDto> {

    private static final long serialVersionUID = 1877078507L;

    public QAllMentorListDto_MentorDto(com.querydsl.core.types.Expression<? extends com.mentormentee.core.domain.User> user, com.querydsl.core.types.Expression<? extends com.mentormentee.core.domain.Department> department) {
        super(AllMentorListDto.MentorDto.class, new Class<?>[]{com.mentormentee.core.domain.User.class, com.mentormentee.core.domain.Department.class}, user, department);
    }

}

