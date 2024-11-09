package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.exception.exceptionCollection.JWTClaimException;
import com.mentormentee.core.repository.*;
import com.mentormentee.core.utils.CourseNameComparator;
import com.mentormentee.core.utils.JwtUtils;
import com.mentormentee.core.utils.MentorComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseMentorService {

    private final CourseMentorRepository courseMentorRepository;
    private final UserTransactionRepository userTransactionRepository;
    private final UserRepository userRepository;
    private final MentorDetailsRepository mentorDetailsRepository;

    public CourseMentorDto getCourseMentorDetails(Long departmentId, String selectedYear, Long courseId, String sortBy, Pageable pageable) {
        UserInformDto userInformDto = getUserinforDto();
        int userYearInUni = userInformDto.getYearInUni();
        CourseYear courseYear = determineCourseYear(selectedYear, userYearInUni);  //선택된 학년 또는 사용자의 학년을 기준으로 과목 학년 결정

        List<Course> courses = courseMentorRepository.findCoursesByDepartmentAndYear(departmentId, courseYear);

        // 코스 ㄱㄴㄷ순 정렬
        courses.sort((c1, c2) -> new CourseNameComparator().compare(c1.getCourseName(), c2.getCourseName()));

        Course selectedCourse = courseId != null ? courseMentorRepository.findById(courseId) : (courses.isEmpty() ? null : courses.get(0));

        // 특정 과목을 수강한 멘토들 다 불러옴
        List<UserCourse> userCourses = selectedCourse == null ? Collections.emptyList() : courseMentorRepository.findUserCoursesByCourse(selectedCourse.getId());  //선택된 과목을 수강한 멘토의 과목정보 조회

        Map<Long, Integer> userCieatStockMap = new HashMap<>();  //사용자ID와 cieatstock 매핑
        Map<Long, Integer> userCieatGradeMap = new HashMap<>();  //사용자ID와 cieatgrade매핑

        //사용자 거래 내역 조회
        for (UserCourse userCourse : userCourses) {
            List<UserTransaction> transactions = userTransactionRepository.findByUser(userCourse.getUser().getId());//멘토 id -> 멘토의 거레 내역
            //씨앗 거래 때마다 씨앗 잔고를 더하는것
            int cieatStock = transactions.stream().mapToInt(transaction -> transaction.getTransaction().getCieatStock()).sum();  //거레네약 히니히나의 cieatamount 총합 계산
            int cieatGrade = transactions.stream().mapToInt(transaction -> transaction.getTransaction().getTransactionAmount()).sum();  //UserTransaction 엔티티의 TransactionAmount에 따라 cieatgrade계산

            userCieatStockMap.put(userCourse.getUser().getId(), cieatStock);
            userCieatGradeMap.put(userCourse.getUser().getId(), cieatGrade);
        }

        //MentorDto 변환
        List<CourseMentorDto.MentorDto> mentorDtos = userCourses.stream()
                .map(userCourse -> {
                    User user = userCourse.getUser();//멘토
                    Course course = userCourse.getCourse();//과목
                    int cieatStock = userCieatStockMap.getOrDefault(user.getId(), 0);  //사용자 ID에 해당하는 cieat 재고 합 조회
                    int cieatGrade = userCieatGradeMap.getOrDefault(user.getId(), 0);  //사용자 ID에 해당하는 cieat 거래량 합 조회
                    return new CourseMentorDto.MentorDto(user, course, userCourse, cieatStock, cieatGrade);//멘토, 과목, 멘토과목, cieat 재고 합, cieat 거래량 합
                })
                .sorted()
                .collect(Collectors.toList());//배열 기준에따라 배열

        //페이징 처리
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), mentorDtos.size());
        List<CourseMentorDto.MentorDto> pagedMentors = mentorDtos.subList(start, end);

        return new CourseMentorDto(selectedCourse == null ? null : selectedCourse.getCourseName(), pagedMentors, mentorDtos.size());
    }

    /**
     * 서비스 레이어에서 동일 계층 bean을 참조하는 부분
     * 순환참조가 일어날 것을 예방하기 위해
     * 서비스 레이어 참조부분을 없에고
     * 리포지토리를 참조하도록 바꾸었습니다
     * - 2024-09-18 최기연 -
     */
    private UserInformDto getUserinforDto() {
        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new JWTClaimException());
        UserInformDto userInformDto;

        if(user.getDepartment() == null) {
            userInformDto = new UserInformDto(user.getNickName(), user.getEmail(), null, user.getYearInUni(), user.getUserProfilePicture());
        }
        else{
            userInformDto = new UserInformDto(user.getNickName(), user.getEmail(), user.getDepartment().getDepartmentName(), user.getYearInUni(), user.getUserProfilePicture());
        }
        return userInformDto;

    }

    private CourseYear determineCourseYear(String selectedYear, int userYearInUni) {
        if (selectedYear == null || selectedYear.isEmpty()) {
            return CourseYear.fromInt(userYearInUni);
        } else {
            try {
                return CourseYear.fromString(selectedYear.toLowerCase());
            } catch (IllegalArgumentException e) {
                return CourseYear.fromInt(userYearInUni);
            }
        }
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

        // MentorDetailsUpdateDto 생성 및 반환
        return new MentorDetailsUpdateDto(
                courseDetailsDtos,
                availabilityDtos,
                user.getWaysOfCommunication().name(),
                user.getSelfIntroduction(),
                user.getUserRole(),
                user.getUserProfilePicture(),
                userCoursesPage.getTotalPages(),
                userCoursesPage.getNumber(),
                userCoursesPage.isLast()
        );
    }

    public MentorDetailsUpdateDto updateMentorDetails(MentorDetailsUpdateDto updateDto, Pageable pageable) {
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
        if (updateDto.getAvailabilities() == null) {
        } else {
            updateAvailability(user, updateDto.getAvailabilities());
        }

        userRepository.save(user);

        // CourseDetails 페이징된 목록 반환
        Page<UserCourse> userCoursesPage = userRepository.findUserCoursesByUser(user, pageable);
        List<CourseDetailsDto> courseDetailsDtos = convertToCourseDetailsDto(userCoursesPage.getContent());

        // AvailableTime 조회 및 변환
        List<AvailableTimeDto> availabilityDtos = convertToAvailableTimeDto(
                userRepository.findAvailabilitiesByUser(user)
        );

        // 수정된 정보를 포함한 MentorDetailsUpdateDto 반환
        return new MentorDetailsUpdateDto(
                courseDetailsDtos,
                availabilityDtos,
                user.getWaysOfCommunication().name(),
                user.getSelfIntroduction(),
                user.getUserRole(),
                user.getUserProfilePicture(),
                userCoursesPage.getTotalPages(),
                userCoursesPage.getNumber(),
                userCoursesPage.isLast()
        );
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
            newUserCourse.setGradeStatus(courseDetailsDto.getGrade() != null ?
                    GradeStatus.valueOf(courseDetailsDto.getGrade()) : null);
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

        if (course.getGrade() == null) {
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

















