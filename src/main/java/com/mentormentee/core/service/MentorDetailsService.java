package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.AvailableTimeDto;
import com.mentormentee.core.dto.CourseDetailsDto;
import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.dto.ReviewDto;
import com.mentormentee.core.repository.MentorDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorDetailsService {

    private final MentorDetailsRepository mentorDetailsRepository;


    //멘토 조회 페이지(사용자 접근 권한 인증 불필요)
    public MentorDetailsDto getMentorDetails(Long userId, Pageable pageable) {
        //페이징 처리된 UserCourse 목록
        Page<UserCourse> userCoursesPage = mentorDetailsRepository.findUserCoursesByUserId(userId, pageable);

        //User 정보를 가져옵니다. (페이지네이션된 목록에서 첫 번째 항목으로부터 User 정보를 가져옵니다)
        User user = null;
        List<UserCourse> userCourses = new ArrayList<>();
        if (!userCoursesPage.getContent().isEmpty()) {
            userCourses = userCoursesPage.getContent();
            user = userCourses.get(0).getUser();
        } else {
            // User 정보를 조회하기 위한 별도의 방법 필요 (예를 들어, userId로 User를 직접 조회)
            user = mentorDetailsRepository.findUserById(userId)
                    .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));
        }

        //UserCourse 목록을 CourseDetailsDto 목록으로 변환
        List<CourseDetailsDto> courseDetailsDtos = userCourses.stream()
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

        //AvailableTime을 별도로 조회하여 중복 제거 후 변환
        List<AvailableTime> availableTimes = mentorDetailsRepository.findAvailabilitiesByUserId(userId);
        List<AvailableTimeDto> availabilityDtos = availableTimes.stream()
                .map(at -> new AvailableTimeDto(
                        at.getId(),  // Availability ID를 추가
                        at.getDayOfWeek(),
                        at.getAvailableStartTime(),
                        at.getAvailableEndTime()
                ))
                .distinct() // 중복 제거
                .collect(Collectors.toList());

        //멘토에 대한 리뷰 조회
        List<ReviewDto> reviews = mentorDetailsRepository.findReviewsByUserId(userId)
                .stream()
                .map(review -> new ReviewDto(review.getComment(), review.getRating(), review.getReviewDate()))
                .collect(Collectors.toList());

        //페이지 정보 계산
        int totalPages = userCoursesPage.getTotalPages();
        int currentPageNum = userCoursesPage.getNumber();
        boolean lastPageOrNot = userCoursesPage.isLast();

        //MentorDetailsDto로 변환
        return new MentorDetailsDto(
                courseDetailsDtos,
                availabilityDtos,
                user.getWaysOfCommunication().name(),
                user.getSelfIntroduction(),
                reviews,
                user.getUserRole(),
                totalPages,
                currentPageNum,
                lastPageOrNot
        );
    }
}











