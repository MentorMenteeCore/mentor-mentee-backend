package com.mentormentee.core.repository;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserCourse;
import com.mentormentee.core.dto.CourseNameDto;
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


    @EntityGraph(attributePaths = {"course"})
    @Query("select c.courseName as courseName, uc.isMajor as isMajor"
            +" from UserCourse uc"
            +" join uc.course c"
            +" where uc.user = :user" +
            " order by uc.course.courseName")
    Page<CourseNameAndMajorOnly> findCoursesByUser(@Param("user") User user, Pageable pageable);


    @EntityGraph(attributePaths = {"user", "course"})
    @Query("select new com.mentormentee.core.dto.CourseNameDto(cn.course.courseName, cn.isMajor)" +
            "from UserCourse cn " +
            "where cn.user= :user " +
            "order by cn.course.courseName")
    List<CourseNameDto> findCourseNameDtoByUser(@Param("user") User user);

    void deleteByUser(User user);
}
