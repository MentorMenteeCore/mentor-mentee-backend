package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.exception.exceptionCollection.JWTClaimException;
import com.mentormentee.core.repository.*;
import com.mentormentee.core.dto.AvailableTimeDto;
import com.mentormentee.core.dto.CourseDetailsDto;
import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.dto.ReviewDto;
import com.mentormentee.core.repository.MentorDetailsRepository;
import com.mentormentee.core.utils.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorDetailsService {

    private final MentorDetailsRepository mentorDetailsRepository;
    private final UserRepository userRepository;

    // 멘토 조회 페이지(사용자 접근 권한 인증 불필요)
    public MentorDetailsDto getMentorDetails(String nickName, Pageable pageable) {
        // 해당 닉네임을 가진 멘토 조회
        User mentor = mentorDetailsRepository.findByNickName(nickName)
                .orElseThrow(() -> new EntityNotFoundException("해당 멘토가 없습니다."));


        // 페이징 처리된 UserCourse 목록
        Page<UserCourse> userCoursesPage = mentorDetailsRepository.findUserCoursesByUser(mentor, pageable);

        // UserCourse 목록을 CourseDetailsDto 목록으로 변환
        List<CourseDetailsDto> courseDetailsDtos = userCoursesPage.getContent().stream()
                .map(userCourse -> {
                    Course course = userCourse.getCourse();
                    return new CourseDetailsDto(
                            userCourse.getId(),
                            course.getCourseName(),
                            course.getCredit(),
                            userCourse.getGradeStatus().getDisplayValue(),
                            course.getProfessor()
                    );
                })
                .collect(Collectors.toList());

        // AvailableTime을 별도로 조회하여 중복 제거 후 변환
        List<AvailableTime> availableTimes = mentorDetailsRepository.findAvailabilitiesByUser(mentor);
        List<AvailableTimeDto> availabilityDtos = availableTimes.stream()
                .map(at -> new AvailableTimeDto(
                        at.getId(),  // Availability ID
                        at.getDayOfWeek(),
                        at.getAvailableStartTime(),
                        at.getAvailableEndTime()
                ))
                .distinct() // 중복 제거
                .collect(Collectors.toList());

        // 멘토에 대한 리뷰 조회
        List<ReviewDto> reviews = mentorDetailsRepository.findReviewsByUser(mentor)
                .stream()
                .map(review -> new ReviewDto(review.getComment(), review.getRating(), review.getReviewDate()))
                .collect(Collectors.toList());

        // 페이지 정보 계산
        int totalPages = userCoursesPage.getTotalPages();
        int currentPageNum = userCoursesPage.getNumber();
        boolean lastPageOrNot = userCoursesPage.isLast();

        // MentorDetailsDto로 변환
        return new MentorDetailsDto(
                courseDetailsDtos,
                availabilityDtos,
                mentor.getWaysOfCommunication().name(),
                mentor.getSelfIntroduction(),
                reviews,
                mentor.getUserRole(),
                totalPages,
                currentPageNum,
                lastPageOrNot
        );
    }
}
