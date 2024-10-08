package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.AvailableTimeDto;
import com.mentormentee.core.dto.CourseDetailsDto;
import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.dto.ReviewDto;
import com.mentormentee.core.exception.exceptionCollection.NickNameIsNotExistedException;
import com.mentormentee.core.repository.MentorDetailsRepository;
import com.mentormentee.core.repository.ReviewRepository;
import com.mentormentee.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserSearchByNicknameService {

    private final MentorDetailsRepository mentorDetailsRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    //멘토 조회 페이지(사용자 접근 권한 인증 불필요)

    public MentorDetailsDto getUserDetailsByUserNickname(Pageable pageable, User user) {
        //페이징 처리된 UserCourse 목록
        Page<UserCourse> userCoursesPage = mentorDetailsRepository.findUserCourseByUser(user, pageable);

        List<UserCourse> userCourseList = userCoursesPage.getContent();

        List<CourseDetailsDto> courseDetailsDtos = userCourseList.stream()
                .map(courseDetailsDto -> {
                    Course course = courseDetailsDto.getCourse();
                    return new CourseDetailsDto(
                            courseDetailsDto.getId(),
                            course.getCourseName(),
                            course.getCredit(),
                            courseDetailsDto.getGradeStatus().getDisplayValue(),
                            course.getProfessor()
                    );
                })
                .collect(Collectors.toList());

        //AvailableTime을 별도로 조회하여 중복 제거 후 변환
        List<AvailableTime> availableTimes = user.getAvailabilities();

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
        List<Review> reviews = reviewRepository.findReviewsByMentor(user);
                List<ReviewDto> reviewDtos =
                reviews.stream()
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
                reviewDtos,
                /**
                 * Role은 따로 빼요!
                 */
                totalPages,
                currentPageNum,
                lastPageOrNot,
                user.getNickName(),
                user.getUserProfilePicture(),
                reviews.size()
        );
    }

    /**
     * 검색한 유저의 역할에 따라 다른 결과가 나오기 때문에
     * 처음에 검색한 유저 가져오는 Api
     * @return
     */
    public User findUserByNickname(String nickname) {
        try {
            User user = userRepository.getUserByNickname(nickname);
            return user;
        }catch (Exception e) {
            throw NickNameIsNotExistedException.EXCEPTION;
        }
    }
}
