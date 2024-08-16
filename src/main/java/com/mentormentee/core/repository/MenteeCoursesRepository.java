package com.mentormentee.core.repository;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 언제사용? : 유저 엔티티를 가지고 유저의 수강 과목을 뽑아오고 싶을때 사용
 */
@Repository
public interface MenteeCoursesRepository extends JpaRepository<UserCourse, Long>{


    @EntityGraph(attributePaths = "course")
    @Query("select c.courseName as courseName"
            +" from UserCourse uc"
            +" join uc.course c"
            +" where uc.user = :user")
    Page<CourseNameOnly> findCoursesByUser(@Param("user") User user, Pageable pageable);

}
