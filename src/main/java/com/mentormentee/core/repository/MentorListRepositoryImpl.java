package com.mentormentee.core.repository;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.MentorListDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.mentormentee.core.domain.QCourse.course;
import static com.mentormentee.core.domain.QUser.user;
import static com.mentormentee.core.domain.QUserCourse.userCourse;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentorListRepositoryImpl implements MentorListRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Course findCourseById(Long courseId) {
        return jpaQueryFactory.selectFrom(course)
                .where(course.id.eq(courseId))
                .fetchOne();
    }


    @Override
    public Page<MentorListDto.MentorDto> findMentors(Long courseId, String sortBy, Pageable pageable) {
        Predicate whereCondition = createMentorWhereCondition(courseId, true);
        OrderSpecifier<?>[] orderSpecifiers = createMentorOrderSpecifiers(sortBy);

        long totalCount = jpaQueryFactory.selectFrom(userCourse)
                .join(userCourse.user, user)
                .where(whereCondition)
                .fetchCount();

        List<UserCourse> userCourses = jpaQueryFactory.selectFrom(userCourse)
                .join(userCourse.user, user)
                .where(whereCondition)
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<MentorListDto.MentorDto> mentorDtos = userCourses.stream()
                .map(userCourse -> {
                    User user = userCourse.getUser();
                    Department department = user.getDepartment();
                    return new MentorListDto.MentorDto(user, userCourse.getCourse(), userCourse, department);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(mentorDtos, pageable, totalCount);
    }


    @Override
    public List<Course> findCoursesByDepartmentAndYear(Long departmentId, CourseYear courseYear) {
        return jpaQueryFactory.selectFrom(course)
                .where(course.department.id.eq(departmentId)
                        .and(course.courseYear.eq(courseYear)))
                .orderBy(course.courseName.asc())
                .fetch();
    }

    private OrderSpecifier<?>[] createMentorOrderSpecifiers(String sortBy) {
        OrderSpecifier<?> nicknameAsc = user.nickName.asc().nullsLast();

        switch (sortBy) {
            case "year":
                return new OrderSpecifier<?>[]{user.yearInUni.asc(), nicknameAsc};
            case "grade":
                OrderSpecifier<?> gradeOrder = new CaseBuilder()
                        .when(userCourse.gradeStatus.eq(GradeStatus.APLUS)).then(1)
                        .when(userCourse.gradeStatus.eq(GradeStatus.A)).then(2)
                        .when(userCourse.gradeStatus.eq(GradeStatus.BPLUS)).then(3)
                        .when(userCourse.gradeStatus.eq(GradeStatus.B)).then(4)
                        .when(userCourse.gradeStatus.eq(GradeStatus.C)).then(5)
                        .otherwise(6).asc();
                return new OrderSpecifier<?>[]{gradeOrder, nicknameAsc};
            case "nickname":
            default:
                return new OrderSpecifier<?>[]{nicknameAsc};
        }
    }

    private Predicate createMentorWhereCondition(Long courseId, boolean isCourseIdRequired) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.userRole.eq(Role.ROLE_MENTOR));

        if (isCourseIdRequired) {
            builder.and(userCourse.course.id.eq(courseId));
        }

        return builder;
    }
}

