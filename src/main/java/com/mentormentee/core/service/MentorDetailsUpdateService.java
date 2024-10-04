package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.AvailableTimeDto;
import com.mentormentee.core.dto.CourseDetailsDto;
import com.mentormentee.core.dto.MentorDetailsDto;
import com.mentormentee.core.dto.MentorDetailsUpdateDto;
import com.mentormentee.core.repository.MentorDetailsRepository;
import com.mentormentee.core.repository.UserRepository;
import com.mentormentee.core.utils.JwtUtils;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
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
    private final MentorDetailsRepository mentorDetailsRepository;  // CourseRepository를 추가
    private final JwtUtils jwtUtils;
    private final MentorDetailsService mentorDetailsService;



    //사용자 접근 권한 검증
    private User validateUserAccess(Long userId) {
        String userEmail = jwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("유저가 존재하지 않습니다."));

        if (!userId.equals(user.getId())) {
            throw new AccessDeniedException("이 사용자 정보에 접근할 수 없습니다.");
        }

        if (user.getUserRole() != Role.ROLE_MENTOR) {
            throw new AccessDeniedException("멘토가 아닙니다.");
        }

        return user;  
    }

    //멘토 정보 조회
    public MentorDetailsDto getMentorDetails(Long userId, Pageable pageable) {
        validateUserAccess(userId);
        return mentorDetailsService.getMentorDetails(userId, pageable);  
    }


    //멘토 정보 추가
    public void postMentorDetails(Long userId, MentorDetailsUpdateDto updateDto) {
        //사용자 접근 권한 검증
        User user = validateUserAccess(userId);

        //자기소개
        if (updateDto.getSelfIntroduction() != null) {  //사용자가 해당 정보의 추가를 요청한 경우
            if (user.getSelfIntroduction() == null || user.getSelfIntroduction().isEmpty()) {  //null이면 정보 추가
                user.changeSelfIntroduction(updateDto.getSelfIntroduction());
            } else {
                throw new IllegalStateException("정보가 존재합니다.");
            }
        }

        //WaysOfCommunication
        if (updateDto.getWaysOfCommunication() != null) {
            if (user.getWaysOfCommunication() == null) {
                user.changeWaysOfCommunication(WaysOfCommunication.valueOf(updateDto.getWaysOfCommunication()));
            } else {
                throw new IllegalStateException("정보가 존재합니다.");
            }
        }

        //availabilities
        if (updateDto.getAvailabilities() != null) {
            //중복 방지
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
            //중복 방지
            Set<UserCourse> existingCourses = new HashSet<>(user.getUserCourse());  //이미 등록한 UserCourse 엔티티들을 Set으로 로드
            for (CourseDetailsDto dto : updateDto.getCourseDetails()) {
                Course course;
                try {
                    //courseName과 professor를 기준으로 Course를 검색
                    course = mentorDetailsRepository.findCourseByNameAndProfessor(dto.getCourseName(), dto.getProfessor());
                } catch (NoResultException e) {
                    throw new IllegalArgumentException("해당 과목이 존재하지 않습니다.");
                }

                //UserCourse 엔티티 생성
                UserCourse newUserCourse = new UserCourse(
                        user, course, GradeStatus.valueOf(dto.getGradeStatus()));

                //기존에 같은 UserCourse가 있는지 확인
                boolean alreadyExists = existingCourses.stream()
                        .anyMatch(uc -> uc.getCourse().equals(course) && uc.getUser().equals(user) && uc.getGradeStatus().equals(newUserCourse.getGradeStatus()));

                if (!alreadyExists) {
                    user.getUserCourse().add(newUserCourse);
                    mentorDetailsRepository.saveUserCourse(newUserCourse); //추가 저장 호출
                }
            }
        }
        //변경된 사용자 정보 저장
        userRepository.save(user);
    }

    
    //멘토 정보 수정
    public MentorDetailsDto updateMentorDetails(Long userId, MentorDetailsUpdateDto updateDto, Pageable pageable) {
        //사용자 접근 권한 검증
        User user = validateUserAccess(userId);

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

        //변경된 사용자 정보 저장
        userRepository.save(user);
        return mentorDetailsService.getMentorDetails(userId, pageable);
    }

    private void updateCourseDetails(User user, List<CourseDetailsDto> courseDetails) {
        if (courseDetails == null) {
            throw new IllegalArgumentException("post요청을 해주세요.");
        }

        //기존의 UserCourse 맵핑
        Map<Long, UserCourse> currentCoursesMap = user.getUserCourse().stream()
                .collect(Collectors.toMap(uc -> uc.getCourse().getId(), uc -> uc));

        Set<Long> newCourseIds = new HashSet<>();

        for (CourseDetailsDto dto : courseDetails) {
            Course course;
            try {
                //courseName과 professor를 기준으로 Course 검색
                course = mentorDetailsRepository.findCourseByNameAndProfessor(dto.getCourseName(), dto.getProfessor());
            } catch (NoResultException e) {
                throw new IllegalArgumentException("해당 과목이 존재하지 않습니다.");
            }

            UserCourse existingCourse = currentCoursesMap.get(course.getId());

            if (existingCourse != null) {
                //기존 UserCourse의 정보 수정
                if (dto.getGradeStatus() != null) {
                    existingCourse.changeInfo(GradeStatus.valueOf(dto.getGradeStatus()));
                }
            } else {
                //수정된 UserCourse 추가
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
            throw new IllegalArgumentException("post요청을 해주세요.");
        }

        //기존의 AvailableTime 정보를 Map으로 변환 (key: dayOfWeek + startTime)
        Map<String, AvailableTime> currentAvailabilitiesMap = user.getAvailabilities().stream()
                .collect(Collectors.toMap(
                        avail -> avail.getDayOfWeek() + "|" + avail.getAvailableStartTime(),
                        avail -> avail
                ));

        //업데이트할 AvailableTime 정보를 Map으로 변환 (key: dayOfWeek + startTime)
        Map<String, AvailableTimeDto> newAvailabilitiesMap = availabilities.stream()
                .collect(Collectors.toMap(
                        dto -> dto.getDayOfWeek() + "|" + dto.getAvailableStartTime(),
                        dto -> dto
                ));

        // 기존 정보를 업데이트하거나 새 항목을 추가
        for (AvailableTimeDto dto : availabilities) {
            String key = dto.getDayOfWeek() + "|" + dto.getAvailableStartTime();
            if (currentAvailabilitiesMap.containsKey(key)) {
                // 기존 항목 업데이트
                AvailableTime existingAvailability = currentAvailabilitiesMap.get(key);

                // 필드 업데이트
                if (dto.getAvailableEndTime() != null) {
                    existingAvailability.setAvailableEndTime(dto.getAvailableEndTime());
                }
                // 다른 필드는 기존 값을 유지하며 수정하지 않습니다.

            } else {
                // 새 항목 추가
                AvailableTime newAvailability = new AvailableTime(user, dto.getDayOfWeek(), dto.getAvailableStartTime(), dto.getAvailableEndTime());
                user.getAvailabilities().add(newAvailability);
            }
        }

    }


    //멘토 정보 삭제(자기소개와 waysofcommunication은 삭제 불가)
    public void deleteMentorDetails(Long userId, List<Long> availabilityId, List<Long> courseDetailsId) {
        User user = validateUserAccess(userId);

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





    //정보를 추가하거나 변경할 때, 데이터를 검증한다. 데이터 요청 시 빠진 데이터가 없는 지 검사.
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

    

    //멘티로 역할 변경
    public void changeRoleToMentee(Long userId) {
        User user = validateUserAccess(userId);

        //멘티로 역할 변경
        user.setUserRole(Role.ROLE_MENTEE);

        //멘토 정보 유지
        userRepository.save(user);
    }
}
