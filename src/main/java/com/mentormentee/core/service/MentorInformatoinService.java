package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.exception.exceptionCollection.JWTClaimException;
import com.mentormentee.core.repository.*;
import com.mentormentee.core.utils.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MentorInformatoinService {

    private final CourseMentorRepository courseMentorRepository;
    private final UserTransactionRepository userTransactionRepository;
    private final UserRepository userRepository;
    private final MentorDetailsRepository mentorDetailsRepository;


    // 멘토 조회 페이지(사용자 접근 권한 인증 불필요, 다른 사용자가 사용)
    public MentorDetailsDto getMentorInformationBynickName(String nickName, Pageable pageable) {
        // 닉네임을 기반으로 User 조회
        User mentor = mentorDetailsRepository.findUserByNickName(nickName)
                .orElseThrow(() -> new EntityNotFoundException("해당 멘토가 없습니다."));


        // 페이징 처리된 UserCourse 목록
        Page<UserCourse> userCoursesPage = mentorDetailsRepository.findUserCoursesByUser(mentor, pageable);

        // UserCourse 목록을 CourseDetailsDto 목록으로 변환
        List<CourseDetailsDto> courseDetailsDtos = userCoursesPage.getContent().stream()
                .map(userCourse -> {
                    Course course = userCourse.getCourse();
                    return new CourseDetailsDto(
                            course.getId(),
                            course.getCourseName(),
                            course.getCredit(),
                            userCourse.getGradeStatus().getDisplayValue()
                    );
                })
                .collect(Collectors.toList());

        // AvailableTime을 별도로 조회하여 중복 제거 후 변환
        List<AvailableTime> availableTimes = mentorDetailsRepository.findAvailabilitiesByUser(mentor);
        List<AvailableTimeDto> availabilityDtos = availableTimes.stream()
                .map(at -> new AvailableTimeDto(
                        at.getId(),
                        at.getDayOfWeek(),
                        at.getAvailableStartTime(),
                        at.getAvailableEndTime()
                ))
                .distinct() //중복 제거
                .collect(Collectors.toList());

        //멘토에 대한 리뷰 조회
        List<ReviewDto> reviews = mentorDetailsRepository.findReviewsByUser(mentor)
                .stream()
                .map(review -> new ReviewDto(review.getComment(), review.getRating(), review.getReviewDate()))
                .collect(Collectors.toList());

        //리뷰 개수 계산
        int reviewCount = reviews.size();

        //페이지 정보 계산
        int totalPages = userCoursesPage.getTotalPages();
        int currentPageNum = userCoursesPage.getNumber();
        boolean lastPageOrNot = userCoursesPage.isLast();

        // MentorDetailsDto로 변환
        return new MentorDetailsDto(
                mentor.getId(),
                courseDetailsDtos,
                availabilityDtos,
                mentor.getWaysOfCommunication().name(),
                mentor.getSelfIntroduction(),
                reviews,
                totalPages,  // totalPages
                currentPageNum,  // currentPageNum
                lastPageOrNot,  // lastPageOrNot
                mentor.getNickName(),  // nickName
                mentor.getUserProfilePicture(),
                reviewCount  // reviewSize
        );
    }
    
    
    
    
    // 멘토 정보 조회
    public MentorDetailsUpdateDto getMentorDetails(Pageable pageable) {
        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new JWTClaimException());

        // CourseDetails 페이징 처리 (3과목씩)
        Page<UserCourse> userCoursesPage = userRepository.findUserCoursesByUser(user, pageable);
        List<CourseDetailsDto> courseDetailsDtos = convertToCourseDetailsDto(userCoursesPage.getContent());

        // 멘토의 AvailableTime 조회 및 변환 (전체)
        List<AvailableTimeDto> availabilityDtos = convertToAvailableTimeDto(
                userRepository.findAvailabilitiesByUser(user)
        );

        List<Review> reviewList = userRepository.findReviewsByUser(user);
        List<ReviewDto> returnReviews = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewDto reviewDto = new ReviewDto(review.getComment(), review.getRating(), review.getReviewDate());
            returnReviews.add(reviewDto);
        }

        // MentorDetailsUpdateDto 생성 및 반환
        return new MentorDetailsUpdateDto(
                courseDetailsDtos,
                availabilityDtos,
                returnReviews,
                user.getId(),
                user.getNickName(),
                user.getWaysOfCommunication().name(),
                user.getSelfIntroduction(),
                user.getUserRole(),
                user.getUserProfilePicture(),
                userCoursesPage.getTotalPages(),
                userCoursesPage.getNumber(),
                userCoursesPage.isLast()
        );
    }

    @Transactional
    public void updateMentorDetails(MentorDetailsUpdateDto updateDto, Pageable pageable) {
        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new JWTClaimException());

        // 자기소개 수정
        if (updateDto.getSelfIntroduction() != null) {
            user.changeSelfIntroduction(updateDto.getSelfIntroduction());
        }

        // 연락 방법 수정
        if (updateDto.getWaysOfCommunication() != null) {
            user.changeWaysOfCommunication(
                    WaysOfCommunication.valueOf(updateDto.getWaysOfCommunication())
            );
        }

        // CourseDetails 수정 및 추가
        if (updateDto.getCourseDetails() == null) {
        } else { // courseDetails가 null이 아닌 경우
            updateCourseDetails(user, updateDto.getCourseDetails());
        }



        // AvailableTime 수정 및 추가
        if (updateDto.getAvailabilities().isEmpty()) {
        }
        else {
            updateAvailability(user, updateDto.getAvailabilities());
        }

        userRepository.save(user);
    }

    // CourseDetails 수정 로직
    public void updateCourseDetails(User user, List<CourseDetailsDto> courseDetailsDtos) {
        // 기존 UserCourse 삭제
        userRepository.deleteUserCoursesByUser(user);

        // 새로운 UserCourse 생성 및 저장
        for (CourseDetailsDto courseDetailsDto : courseDetailsDtos) {
            Course newCourse = userRepository.findCourseByName(courseDetailsDto.getCourseName())
                    .orElseThrow(() -> new IllegalArgumentException("해당 과목이 존재하지 않습니다."));

            UserCourse newUserCourse = new UserCourse();
            newUserCourse.setCourse(newCourse);
            newUserCourse.setGradeStatus(courseDetailsDto.getGradeStatus() != null ?
                    GradeStatus.valueOf(courseDetailsDto.getGradeStatus()) : null);
            newUserCourse.setUser(user);

            mentorDetailsRepository.save(newUserCourse); // 저장 로직
        }
    }

    public void updateAvailability(User user, List<AvailableTimeDto> availableTimeDtos) {
        // 기존의 Availability를 삭제
        userRepository.deleteAllAvailableTimes(user); // 모든 Availability 삭제

        // 새로운 Availability 정보를 저장
        for (AvailableTimeDto availableTimeDto : availableTimeDtos) {
            AvailableTime newAvailability = new AvailableTime();
            newAvailability.setDayOfWeek(availableTimeDto.getDayOfWeek());
            newAvailability.setAvailableStartTime(availableTimeDto.getAvailableStartTime());
            newAvailability.setAvailableEndTime(availableTimeDto.getAvailableEndTime());
            newAvailability.setUser(user);

            userRepository.save(newAvailability);
        }
    }

    // 변환 메서드
    private List<CourseDetailsDto> convertToCourseDetailsDto(List<UserCourse> userCourses) {
        return userCourses.stream()
                .map(userCourse -> new CourseDetailsDto(
                        userCourse.getId(),
                        userCourse.getCourse().getCourseName(),
                        userCourse.getCourse().getCredit(),
                        userCourse.getGradeStatus() != null ? userCourse.getGradeStatus().getDisplayValue() : null // GradeStatus
                ))
                .collect(Collectors.toList());
    }

    // AvailableTime 변환 메서드
    private List<AvailableTimeDto> convertToAvailableTimeDto(List<AvailableTime> availableTimes) {
        return availableTimes.stream()
                .map(at -> new AvailableTimeDto(
                        at.getId(),
                        at.getDayOfWeek(),
                        at.getAvailableStartTime(),
                        at.getAvailableEndTime()
                ))
                .collect(Collectors.toList());
    }









    //정보를 추가하거나 변경할 때, 데이터를 검증한다.
    public String validateMentorDetails(MentorDetailsUpdateDto dto) {
        StringBuilder validationMessage = new StringBuilder();

        if (dto.getCourseDetails() != null) {
            for (CourseDetailsDto course : dto.getCourseDetails()) {
                String courseValidationMessage = validateCourseDetails(course);
                if (courseValidationMessage != null) {
                    validationMessage.append(courseValidationMessage).append(" ");
                }
            }
        }

        if (dto.getAvailabilities() != null) {
            for (AvailableTimeDto availability : dto.getAvailabilities()) {
                String availabilityValidationMessage = validateAvailability(availability);
                if (availabilityValidationMessage != null) {
                    validationMessage.append(availabilityValidationMessage).append(" ");
                }
            }
        }

        return validationMessage.length() > 0 ? validationMessage.toString().trim() : null;
    }


    private String validateCourseDetails(CourseDetailsDto course) {
        StringBuilder missingInfo = new StringBuilder();

        if (course.getCourseName() == null) {
            missingInfo.append("과목 이름이 필요합니다. ");
        }

        if (course.getCredit() <= 0) {
            missingInfo.append("학점이 필요합니다. ");
        }

        if (course.getGradeStatus() == null) {
            missingInfo.append("성적이 필요합니다. ");
        }

        return missingInfo.length() > 0 ? missingInfo.toString().trim() : null;
    }

    private String validateAvailability(AvailableTimeDto availability) {
        StringBuilder missingInfo = new StringBuilder();

        if (availability.getDayOfWeek() == null) {
            missingInfo.append("요일이 필요합니다. ");
        }

        if (availability.getAvailableStartTime() == null) {
            missingInfo.append("시작 시간이 필요합니다. ");
        }

        if (availability.getAvailableEndTime() == null) {
            missingInfo.append("종료 시간이 필요합니다. ");
        }

        return missingInfo.length() > 0 ? missingInfo.toString().trim() : null;
    }
}