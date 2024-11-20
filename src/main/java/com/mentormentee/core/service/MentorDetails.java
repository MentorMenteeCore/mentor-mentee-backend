package com.mentormentee.core.service;

import com.mentormentee.core.domain.Review;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserCourse;
import com.mentormentee.core.dto.AvailableTimeDto;
import com.mentormentee.core.dto.CourseDetailsDto;
import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.dto.ReviewDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MentorDetails {

    List<CourseDetailsDto> getMentorCompletedCourses(Page<UserCourse> userCoursesPage);

    List<AvailableTimeDto> getMentorAvailableTime(User user);

    List<ReviewDto> getMentorReviews(List<Review> reviews);

    MentorDetailsDto getFinalMentorDtoAndReturn(User user, List<CourseDetailsDto> mentorCompletedCourses, List<AvailableTimeDto> mentorAvailableTimes, List<ReviewDto> mentorReviews, Page<UserCourse> mentorCompletedCourseWithPageType, List<Review> reviews);
}
