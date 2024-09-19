package com.mentormentee.core.repository;

import com.mentormentee.core.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseMentorRepository {

    @PersistenceContext
    private final EntityManager em;

    //과목 조회
    public Course findById(Long courseId) {
        return em.find(Course.class, courseId);
    }

    //특정 과목을 수강한 멘토 조회
    public List<UserCourse> findUserCoursesByCourse(Long courseId) {
        return em.createQuery(
                        "select uc from UserCourse uc join fetch uc.user u where uc.course.id = :courseId and u.userRole = :role",
                        UserCourse.class)
                .setParameter("courseId", courseId)
                .setParameter("role", Role.ROLE_MENTOR)
                .getResultList();
    }

    //특정 과목-학년에 따른 과목 조회
    public List<Course> findCoursesByDepartmentAndYear(Long departmentId, CourseYear courseYear) {
        return em.createQuery("select c from Course c where c.department.id = :departmentId and c.courseYear = :courseYear", Course.class)
                .setParameter("departmentId", departmentId)
                .setParameter("courseYear", courseYear)
                .getResultList();
    }
}






