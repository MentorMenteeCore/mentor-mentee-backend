package com.mentormentee.core.repository;

import com.mentormentee.core.domain.Role;
import com.mentormentee.core.dto.AllMentorListDto;
import com.mentormentee.core.dto.QAllMentorListDto_MentorDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mentormentee.core.domain.QDepartment.department;
import static com.mentormentee.core.domain.QUser.user;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AllMentorListRepositoryImpl implements AllMentorListRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AllMentorListDto> findAllMentors(String sortBy, Pageable pageable) {
        // 멘토 목록 조회 조건 (멘토 역할 필터링)
        Predicate whereCondition = createMentorWhereCondition();

        // 정렬 조건 생성
        OrderSpecifier<?>[] orderSpecifiers = createMentorOrderSpecifiers(sortBy);

        // 전체 멘토 수 조회
        long totalCount = jpaQueryFactory.selectFrom(user)
                .where(whereCondition)
                .fetchCount();

        // 정렬과 페이징을 적용하여 멘토 목록 조회
        List<AllMentorListDto.MentorDto> mentorDtos = jpaQueryFactory.select(
                        new QAllMentorListDto_MentorDto( // 수정된 부분
                                user,
                                department
                        )
                )
                .from(user)
                .leftJoin(user.department, department)
                .where(whereCondition)
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        // 페이징 정보 계산
        int totalPages = (int) Math.ceil((double) totalCount / pageable.getPageSize());
        int currentPageNum = pageable.getPageNumber() + 1; // 1부터 시작하는 페이지 번호
        boolean lastPageOrNot = currentPageNum == totalPages;

        // AllMentorListDto 생성자에 mentorDtos와 페이지 정보 전달
        AllMentorListDto allMentorListDto = new AllMentorListDto(
                mentorDtos, totalPages, currentPageNum, lastPageOrNot
        );

        // PageImpl로 반환
        return new PageImpl<>(List.of(allMentorListDto), pageable, totalCount);
    }

    private OrderSpecifier<?>[] createMentorOrderSpecifiers(String sortBy) {
        // 기본 정렬 기준인 닉네임 순 정렬
        OrderSpecifier<?> nicknameAsc = user.nickName.asc().nullsLast();

        // 선택된 정렬 기준에 따라 다른 정렬 기준 설정
        switch (sortBy) {
            case "department": // 학과 이름 순 정렬
                OrderSpecifier<?> departmentNameAsc = department.departmentName.asc().nullsLast();
                return new OrderSpecifier<?>[]{departmentNameAsc, nicknameAsc};

            case "year": // 학년 순 정렬
                return new OrderSpecifier<?>[]{user.yearInUni.asc(), nicknameAsc};

            case "nickname": // 닉네임 순 정렬
            default:
                return new OrderSpecifier<?>[]{nicknameAsc};
        }
    }

    private Predicate createMentorWhereCondition() {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.userRole.eq(Role.ROLE_MENTOR)); // 멘토 역할 필터링
        return builder;
    }
}



