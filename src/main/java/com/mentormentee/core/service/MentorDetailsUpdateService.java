package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.exception.exceptionCollection.JWTClaimException;
import com.mentormentee.core.repository.MentorDetailsRepository;
import com.mentormentee.core.repository.UserRepository;
import com.mentormentee.core.utils.JwtUtils;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MentorDetailsUpdateService {

    private final UserRepository userRepository;
    private final MentorDetailsRepository mentorDetailsRepository;
    private final JwtUtils jwtUtils;
    private final MentorDetailsService mentorDetailsService;

    //사용자 접근 권한 검증
    private User validateUserAccess() {
        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new JWTClaimException());

        if (user.getUserRole() != Role.ROLE_MENTOR) {
            throw new AccessDeniedException("멘토가 아닙니다.");
        }
        return user;
    }

    //멘토 정보 조회
    public MentorDetailsDto getMentorDetails(Pageable pageable) {

        User user = validateUserAccess();
        // 페이징 처리된 UserCourse 목록
        Page<UserCourse> userCoursesPage = mentorDetailsRepository.findUserCoursesByUser(user, pageable);

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
        List<AvailableTime> availableTimes = mentorDetailsRepository.findAvailabilitiesByUser(user);
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
        List<ReviewDto> reviews = mentorDetailsRepository.findReviewsByUser(user)
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
                user.getWaysOfCommunication().name(),
                user.getSelfIntroduction(),
                reviews,
                user.getUserRole(),
                totalPages,
                currentPageNum,
                lastPageOrNot
        );
    }



    //멘토 정보 추가
    public void postMentorDetails(MentorDetailsUpdateDto updateDto) {
        User user = validateUserAccess();

        //자기소개
        if (updateDto.getSelfIntroduction() != null) {
            user.changeSelfIntroduction(updateDto.getSelfIntroduction());
        }


        //WaysOfCommunication
        if (updateDto.getWaysOfCommunication() != null) {
            user.changeWaysOfCommunication(WaysOfCommunication.valueOf(updateDto.getWaysOfCommunication()));
        }


        //availabilities
        if (updateDto.getAvailabilities() != null) {
            Set<AvailableTime> existingAvailabilities = new HashSet<>(user.getAvailabilities());
            for (AvailableTimeDto dto : updateDto.getAvailabilities()) {
                AvailableTime newAvailability = new AvailableTime(user, dto.getDayOfWeek(), dto.getAvailableStartTime(), dto.getAvailableEndTime());
                if (!existingAvailabilities.contains(newAvailability)) {
                    user.getAvailabilities().add(newAvailability);
                }
            }
        }

        //courseDetails
        if (updateDto.getCourseDetails() != null) {
            Set<UserCourse> existingCourses = new HashSet<>(user.getUserCourse());
            for (CourseDetailsDto dto : updateDto.getCourseDetails()) {
                Course course;
                try {
                    course = mentorDetailsRepository.findCourseByNameAndProfessor(dto.getCourseName(), dto.getProfessor());
                } catch (NoResultException e) {
                    throw new IllegalArgumentException("해당 과목이 존재하지 않습니다.");
                }

                UserCourse newUserCourse = new UserCourse(user, course, GradeStatus.valueOf(dto.getGradeStatus()));

                boolean alreadyExists = existingCourses.stream()
                        .anyMatch(uc -> uc.getCourse().equals(course) && uc.getUser().equals(user) && uc.getGradeStatus().equals(newUserCourse.getGradeStatus()));

                if (!alreadyExists) {
                    user.getUserCourse().add(newUserCourse);
                    mentorDetailsRepository.saveUserCourse(newUserCourse);
                }
            }
        }
        userRepository.save(user);
    }

    //멘토 정보 수정
    public MentorDetailsDto updateMentorDetails(MentorDetailsUpdateDto updateDto, String nickName, Pageable pageable) {
        User user = validateUserAccess();

        //자기소개
        if (updateDto.getSelfIntroduction() != null) {
            user.changeSelfIntroduction(updateDto.getSelfIntroduction());
        }

        //WaysOfCommunication
        if (updateDto.getWaysOfCommunication() != null) {
            user.changeWaysOfCommunication(WaysOfCommunication.valueOf(updateDto.getWaysOfCommunication()));
        }

        //availabilities
        if (updateDto.getAvailabilities() != null) {
            updateAvailability(user, updateDto.getAvailabilities());
        }

        //courseDetails
        if (updateDto.getCourseDetails() != null) {
            updateCourseDetails(user, updateDto.getCourseDetails());
        }

        userRepository.save(user);
        return mentorDetailsService.getMentorDetails(nickName, pageable);
    }

    private void updateCourseDetails(User user, List<CourseDetailsDto> courseDetails) {
        if (courseDetails == null) {
            throw new IllegalArgumentException("post 요청을 해주세요.");
        }

        Map<Long, UserCourse> currentCoursesMap = user.getUserCourse().stream()
                .collect(Collectors.toMap(uc -> uc.getCourse().getId(), uc -> uc));

        Set<Long> newCourseIds = new HashSet<>();

        for (CourseDetailsDto dto : courseDetails) {
            Course course;
            try {
                course = mentorDetailsRepository.findCourseByNameAndProfessor(dto.getCourseName(), dto.getProfessor());
            } catch (NoResultException e) {
                throw new IllegalArgumentException("해당 과목이 존재하지 않습니다.");
            }

            UserCourse existingCourse = currentCoursesMap.get(course.getId());

            if (existingCourse != null) {
                if (dto.getGradeStatus() != null) {
                    existingCourse.changeInfo(GradeStatus.valueOf(dto.getGradeStatus()));
                }
            } else {
                UserCourse newUserCourse = new UserCourse(user, course, GradeStatus.valueOf(dto.getGradeStatus()));
                user.getUserCourse().add(newUserCourse);
            }

            if (dto.getCredit() != course.getCredit()) {
                course.changeCourseInfo(dto.getCourseName(), dto.getCredit());
            }

            newCourseIds.add(course.getId());
        }
    }

    private void updateAvailability(User user, List<AvailableTimeDto> availabilities) {
        if (availabilities == null) {
            throw new IllegalArgumentException("post 요청을 해주세요.");
        }

        Map<String, AvailableTime> currentAvailabilitiesMap = user.getAvailabilities().stream()
                .collect(Collectors.toMap(avail -> avail.getDayOfWeek() + "|" + avail.getAvailableStartTime(), avail -> avail));

        Map<String, AvailableTimeDto> newAvailabilitiesMap = availabilities.stream()
                .collect(Collectors.toMap(dto -> dto.getDayOfWeek() + "|" + dto.getAvailableStartTime(), dto -> dto));

        for (AvailableTimeDto dto : availabilities) {
            String key = dto.getDayOfWeek() + "|" + dto.getAvailableStartTime();
            if (currentAvailabilitiesMap.containsKey(key)) {
                AvailableTime existingAvailability = currentAvailabilitiesMap.get(key);
                if (dto.getAvailableEndTime() != null) {
                    existingAvailability.setAvailableEndTime(dto.getAvailableEndTime());
                }
            } else {
                AvailableTime newAvailability = new AvailableTime(user, dto.getDayOfWeek(), dto.getAvailableStartTime(), dto.getAvailableEndTime());
                user.getAvailabilities().add(newAvailability);
            }
        }
    }

    //멘토 정보 삭제(자기소개와 waysofcommunication은 삭제 불가)
    public void deleteMentorDetails(List<Long> availabilityId, List<Long> courseDetailsId) {
        User user = validateUserAccess();

        if (availabilityId != null && !availabilityId.isEmpty()) {
            for (Long id : availabilityId) {
                deleteAvailability(user, id);
            }
        }

        if (courseDetailsId != null && !courseDetailsId.isEmpty()) {
            for (Long id : courseDetailsId) {
                deleteCourseDetails(id);
            }
        }

        userRepository.save(user);
    }

    private void deleteAvailability(User user, Long availabilityId) {
        user.getAvailabilities().removeIf(avail -> avail.getId().equals(availabilityId));
    }

    private void deleteCourseDetails(Long courseDetailsId) {
        mentorDetailsRepository.deleteUserCourseById(courseDetailsId);
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
