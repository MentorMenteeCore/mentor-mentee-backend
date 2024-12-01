package com.mentormentee.core.service;

import com.mentormentee.core.domain.CollegeName;
import com.mentormentee.core.domain.Course;
import com.mentormentee.core.domain.Department;
import com.mentormentee.core.dto.DepartmentDto;
import com.mentormentee.core.dto.SearchDto;
import com.mentormentee.core.repository.CourseRepository;
import com.mentormentee.core.repository.DepartmentRepository;
import com.mentormentee.core.utils.RedisUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor//파이널이 있는 필드만 가지고 생성자를 호출해 준다.
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final RedisUtil redisUtil;
    private final CourseRepository courseRepository;


    @PostConstruct
    public void loadDepartmentsAndCoursesToRedis() {
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            List<Course> courses = courseRepository.findByDepartment(department);
            List<String> courseNames = courses.stream()
                    .map(Course::getCourseName)
                    .collect(Collectors.toList());
            if (courseNames.isEmpty()) {
                redisUtil.setHashValue("departments", department.getDepartmentName(), null);
            } else {
                redisUtil.setHashValue("departments", department.getDepartmentName(), courseNames);
            }
        }
    }


    public List<String> getCoursesByDepartmentName(String departmentName) {
        // Redis에서 학과의 과목 리스트 조회
        Object courses = redisUtil.getHashValue("departments", departmentName);
        if (courses == null) {
            return null;
        } else {
            return (List<String>) courses;
        }
    }

    /**
     * 컨트롤러에서 단과대 이름 받아오면
     * 리포지토리에 가서 Departments 리스트 뽑아옴.
     */
    public List<DepartmentDto> findDepartmentsByCollege(CollegeName college) {
        List<DepartmentDto> departmentByCollege = departmentRepository.findDepartmentByCollege(college);
        return departmentByCollege;
    }


    /**
     * 학과 이름을 가지고 연관된 Image URL과
     * 단과대 이름을 반환합니다.
     */
    public SearchDto findDepartmentAndCollege(String departmentName) {
        Department departmentByName = departmentRepository.findDepartmentByName(departmentName);

        CollegeName collegeName = departmentByName.getCollege().getCollegeName();
        String collegeKoreanName = collegeName.getCollegeKoreanName();
        String searchedDepartmentName = departmentByName.getDepartmentName();
        String imageUrl = departmentByName.getDepartmentImageUrl();

        SearchDto searchDto = new SearchDto(imageUrl,searchedDepartmentName,collegeKoreanName);
        return searchDto;

    }

    public List<String> getDepartmentCourses(String departmentName) {
        Department departmentByName = departmentRepository.findDepartmentByName(departmentName);
        return courseRepository.getCourseListByDepartmentId(departmentByName);
    }
}