package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.repository.CourseMentorRepository;
import com.mentormentee.core.utils.CourseNameComparator;
import com.mentormentee.core.utils.MentorComparator;
import lombok.RequiredArgsConstructor;
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
    private final UserService userService;

    public CourseMentorDto getCourseMentorDetails(Long departmentId, String selectedYear, Long courseId, String sortBy, Pageable pageable) {
        UserInformDto userInformDto = userService.getUserinformation();  //사용자 학년 정보 가져오기
        int userYearInUni = userInformDto.getYearInUni();
        CourseYear courseYear = determineCourseYear(selectedYear, userYearInUni);  //선택된 학년 또는 사용자의 학년을 기준으로 과목 학년 결정

        List<Course> courses = courseMentorRepository.findCoursesByDepartmentAndYear(departmentId, courseYear);
        //코스 ㄱㄴㄷ순 정렬
        courses.sort((c1, c2) -> new CourseNameComparator().compare(c1.getCourseName(), c2.getCourseName()));

        List<CourseMentorDto.CourseDto> courseDtoList = courses.stream()
                .map(CourseMentorDto.CourseDto::new)
                .collect(Collectors.toList());

        Course selectedCourse = courseId != null ? courseMentorRepository.findById(courseId) : (courses.isEmpty() ? null : courses.get(0));

        //특정 과목을 수강한 멘토들 다 불러옴
        List<UserCourse> userCourses = selectedCourse == null ? Collections.emptyList() : courseMentorRepository.findUserCoursesByCourse(selectedCourse.getId());  //선택된 과목을 수강한 사용자의 과목정보 조회

        //MentorDto 변환
        List<CourseMentorDto.MentorDto> mentorDtos = userCourses.stream()
                .map(userCourse -> new CourseMentorDto.MentorDto(userCourse.getUser(), userCourse.getCourse(), userCourse, 0, 0))
                .sorted(new MentorComparator(sortBy))
                .collect(Collectors.toList());  //정렬 기준에 따라 출력

        //페이징 처리
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), mentorDtos.size());  //페이지 끝 위치 계산
        List<CourseMentorDto.MentorDto> pagedMentors = mentorDtos.subList(start, end);  //mentorDtos 리스트에서 start 인덱스부터 end 인덱스까지의 서브리스트를 추출

        // 페이지 정보 계산
        int totalPages = (int) Math.ceil((double) mentorDtos.size() / pageable.getPageSize());
        int currentPageNum = pageable.getPageNumber();
        boolean lastPageOrNot = currentPageNum == totalPages - 1;

        return new CourseMentorDto(
                selectedCourse == null ? null : selectedCourse.getCourseName(),
                pagedMentors,
                mentorDtos.size(),
                courseDtoList,
                totalPages,
                currentPageNum,
                lastPageOrNot
        );
    }

    //처음에 자동으로 사용자의 학년에 맞추어 과목 조회
    private CourseYear determineCourseYear(String selectedYear, int userYearInUni) {
        if (selectedYear == null || selectedYear.isEmpty()) {
            return CourseYear.fromInt(userYearInUni);
        } else {
            try {
                return CourseYear.fromString(selectedYear.toLowerCase());
            } catch (IllegalArgumentException e) {   //예외 발생 시 사용자의 학년을 기준으로 CourseYear 반환
                return CourseYear.fromInt(userYearInUni);
            }
        }
    }
}
