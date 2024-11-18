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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    private final MentorDetails mentorDetails;

    //멘토 조회 페이지(사용자 접근 권한 인증 불필요)
    public MentorDetailsDto getUserDetailsByUserNickname(Pageable pageable, User user) {
        User mergedUser = userRepository.merge(user);

        Page<UserCourse> mentorCompletedCourseWithPageType = mentorDetailsRepository.findUserCourseByUser(user, pageable);
        List<CourseDetailsDto> mentorCompletedCourses = mentorDetails.getMentorCompletedCourses(mentorCompletedCourseWithPageType);

        List<AvailableTimeDto> mentorAvailableTimes = mentorDetails.getMentorAvailableTime(mergedUser);

        List<Review> reviews = reviewRepository.findReviewsByMentor(user);
        List<ReviewDto> reviewsFromMentee = mentorDetails.getMentorReviews(reviews);

        return mentorDetails.getFinalMentorDtoAndReturn(user, mentorCompletedCourses, mentorAvailableTimes, reviewsFromMentee, mentorCompletedCourseWithPageType, reviews);
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
