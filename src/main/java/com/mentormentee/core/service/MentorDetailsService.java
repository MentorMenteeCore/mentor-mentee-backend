package com.mentormentee.core.service;

import com.mentormentee.core.domain.Course;
import com.mentormentee.core.domain.IsMajor;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserCourse;
import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.dto.ReviewDto;
import com.mentormentee.core.repository.MentorDetailsRepository;
import com.mentormentee.core.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorDetailsService {

    private final MentorDetailsRepository mentorDetailsRepository;

    //멘토 정보 조회
    public MentorDetailsDto getMentorDetails(Long userId) {
        //CourseMentorDto에 의해 반환된 userId를 활용하여 해당 멘토의 usercourse 정보 조회
        UserCourse userCourse = mentorDetailsRepository.findUserCourseByUserId(userId);
        if (userCourse == null) {
            throw new NoSuchElementException("사용자의 과목 정보가 존재하지 않습니다.");
        }

        //해당 멘토의 CourseName, credit, gradeStatus 조회
        User user = userCourse.getUser(); //UserCourse 엔티티를 통해 User 엔티티에 접근(AvailableStartTime, AvailableEndTime, WaysOfCommunication, SelfIntroduction 정보 조회)ㄴ
        Course course = userCourse.getCourse();
        //해당 멘토에 대한 리뷰 조회
        List<ReviewDto> reviews = mentorDetailsRepository.findReviewsByUserId(userId)
                .stream()
                .map(review -> new ReviewDto(review.getComment(), review.getRating(), review.getReviewDate())) //Review 객체를 ReviewDto 객체로 변환
                .collect(Collectors.toList()); //변환된 ReviewDto 객체들을 리스트로 반환

        //MentorDetailsDto로 변환
        return new MentorDetailsDto(
                course.getCourseName(),
                course.getCredit(),
                userCourse.getIsMajor() == IsMajor.MAJOR,
                userCourse.getGradeStatus().getDisplayValue(),
                user.getAvailableStartTime(),
                user.getAvailableEndTime(),
                user.getWaysOfCommunication().name(),
                user.getSelfIntroduction(),
                reviews
        );
    }
}