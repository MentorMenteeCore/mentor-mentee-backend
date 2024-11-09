package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.AvailableTimeDto;
import com.mentormentee.core.dto.CourseDetailsDto;
import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MentorDetailsInitialImpl implements MentorDetails {

    @Override
    public List<CourseDetailsDto> getMentorCompletedCourses(Page<UserCourse> userCoursesPage) {

        List<UserCourse> userCourseList = userCoursesPage.getContent();

        List<CourseDetailsDto> courseDetailsDtos = userCourseList.stream()
                .map(courseDetailsDto -> {
                    Course course = courseDetailsDto.getCourse();
                    return new CourseDetailsDto(
                            course.getCourseName(),
                            course.getCredit(),
                            courseDetailsDto.getGradeStatus().getDisplayValue()
                    );
                })
                .collect(Collectors.toList());
        return courseDetailsDtos;
    }

    @Override
    public List<AvailableTimeDto> getMentorAvailableTime(User user) {
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
        return availabilityDtos;
    }

    @Override
    public List<ReviewDto> getMentorReviews(List<Review> reviews) {
        List<ReviewDto> reviewDtos =
                reviews.stream()
                        .map(review -> new ReviewDto(review.getComment(), review.getRating(), review.getReviewDate()))
                        .collect(Collectors.toList());
        return reviewDtos;
    }

    @Override
    public MentorDetailsDto getFinalMentorDtoAndReturn(User user, List<CourseDetailsDto> mentorCompletedCourses, List<AvailableTimeDto> mentorAvailableTimes, List<ReviewDto> mentorReviews, Page<UserCourse> mentorCompletedCourseWithPageType, List<Review> reviews) {
        return new MentorDetailsDto(
                user.getId(),
                mentorCompletedCourses,
                mentorAvailableTimes,
                user.getWaysOfCommunication().name(),
                user.getSelfIntroduction(),
                mentorReviews,
                mentorCompletedCourseWithPageType.getTotalPages(),
                mentorCompletedCourseWithPageType.getNumber(),
                mentorCompletedCourseWithPageType.isLast(),
                user.getNickName(),
                user.getUserProfilePicture(),
                reviews.size()
        );
    }


}
