package com.mentormentee.core.service;

import com.mentormentee.core.domain.Course;
import com.mentormentee.core.domain.CourseYear;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.MentorListDto;
import com.mentormentee.core.dto.UserInformDto;
import com.mentormentee.core.exception.exceptionCollection.JWTClaimException;
import com.mentormentee.core.repository.MentorListRepository;
import com.mentormentee.core.repository.UserRepository;
import com.mentormentee.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorListService {

    private final UserRepository userRepository;
    private final MentorListRepository mentorListRepository;


    // MentorListDto를 반환하는 서비스 메서드
    public MentorListDto getMentorList(Long departmentId, String selectedYear, Long courseId, String sortBy, Pageable pageable) {
        // 사용자 정보 가져오기
        UserInformDto userInformDto = getUserInformDto();
        int userYearInUni = userInformDto.getYearInUni();

        // 학년과 연도를 기반으로 CourseYear 결정
        CourseYear courseYear = determineCourseYear(selectedYear, userYearInUni);

        // 학과 및 연도에 해당하는 강좌 목록 가져오기
        List<Course> courses = mentorListRepository.findCoursesByDepartmentAndYear(departmentId, courseYear);
        List<MentorListDto.CourseDto> courseDtoList = courses.stream()
                .map(MentorListDto.CourseDto::new)
                .collect(Collectors.toList());

        // courseId가 제공되지 않으면 첫 번째 강좌를 선택
        Course selectedCourse = (courseId != null)
                ? mentorListRepository.findCourseById(courseId)
                : (!courses.isEmpty() ? courses.get(0) : null);

        // 선택한 강좌에 해당하는 멘토 목록 가져오기
        Page<MentorListDto.MentorDto> mentorsPage;
        if (selectedCourse == null) {
            mentorsPage = new PageImpl<>(Collections.emptyList(), pageable, 0); // 빈 페이지 반환
        } else {
            mentorsPage = mentorListRepository.findMentors(selectedCourse.getId(), sortBy, pageable); // Repository에서 멘토 리스트 조회
        }

        // MentorListDto 생성하여 반환
        return new MentorListDto(
                selectedCourse == null ? null : selectedCourse.getCourseName(),
                mentorsPage.getContent(),
                courseDtoList,
                mentorsPage.getTotalPages(),
                mentorsPage.getNumber(),
                mentorsPage.isLast(),
                userYearInUni
        );
    }



    private UserInformDto getUserInformDto() {
        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(JWTClaimException::new);
        return new UserInformDto(user.getNickName(), user.getEmail(),
                user.getDepartment() != null ? user.getDepartment().getDepartmentName() : null,
                user.getYearInUni(), user.getUserProfilePicture());
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
}
