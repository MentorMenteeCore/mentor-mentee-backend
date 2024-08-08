package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.repository.CourseMentorRepository;
import com.mentormentee.core.repository.UserTransactionRepository;
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
    private final UserTransactionRepository userTransactionRepository;

    public CourseMentorDto getCourseMentorDetails(Long departmentId, String selectedYear, Long courseId, String sortBy, Pageable pageable) {
        UserInformDto userInformDto = userService.getUserinformation(); //사용자 학년 정보 가져오기
        int userYearInUni = userInformDto.getYearInUni();
        CourseYear courseYear = determineCourseYear(selectedYear, userYearInUni);  //선택된 학년 또는 사용자의 학년을 기준으로 과목 학년 결정

        List<Course> courses = courseMentorRepository.findCoursesByDepartmentAndYear(departmentId, courseYear);

        // 코스 ㄱㄴㄷ순 정렬
        courses.sort((c1, c2) -> new CourseNameComparator().compare(c1.getCourseName(), c2.getCourseName()));

        Course selectedCourse = courseId != null ? courseMentorRepository.findById(courseId) : (courses.isEmpty() ? null : courses.get(0));

        // 특정 과목을 수강한 멘토들 다 불러옴
        List<UserCourse> userCourses = selectedCourse == null ? Collections.emptyList() : courseMentorRepository.findUserCoursesByCourse(selectedCourse.getId());  //선택된 과목을 수강한 사용자의 과목정보 조회

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
                .sorted(new MentorComparator(sortBy))
                .collect(Collectors.toList());//배열 기준에따라 배열

        //페이징 처리
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), mentorDtos.size());
        List<CourseMentorDto.MentorDto> pagedMentors = mentorDtos.subList(start, end);

        return new CourseMentorDto(selectedCourse == null ? null : selectedCourse.getCourseName(), pagedMentors, mentorDtos.size());
    }

    //처음에 자동으로 사용자의 학년에 맞추어 과목 조회
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

















