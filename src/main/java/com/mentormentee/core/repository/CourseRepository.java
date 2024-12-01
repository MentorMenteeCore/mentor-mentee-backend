package com.mentormentee.core.repository;

import com.mentormentee.core.domain.Course;
import com.mentormentee.core.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseName(String courseName);

    List<Course> findByDepartment(Department department);

    @Query("select c.courseName from Course c where c.department = :department")
    List<String> getCourseListByDepartmentId(Department department);
}
