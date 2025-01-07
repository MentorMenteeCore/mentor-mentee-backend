package com.mentormentee.core.repository;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserCourse;
import com.mentormentee.core.dto.CourseNameDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 언제사용? : 유저 엔티티를 가지고 유저의 수강 과목을 뽑아오고 싶을때 사용
 */
@Repository
public interface MenteeCoursesRepository extends JpaRepository<UserCourse, Long>{


    @EntityGraph(attributePaths = {"course","course.department"})
    @Query("select uc.id as id, c.courseName as courseName, uc.isMajor as isMajor, dp.departmentName as departmentName, uc.gradeStatus as grade"
            +" from UserCourse uc"
            +" join uc.course c"
            +" join c.department dp"
            +" where uc.user = :user" +
            " order by uc.course.courseName")
    Page<CourseNameAndMajorOnly> findCoursesByUser(@Param("user") User user, Pageable pageable);


//    @EntityGraph(attributePaths = {"user", "course"})
//    @Query("select new com.mentormentee.core.dto.CourseNameDto(cn.course.courseName, cn.isMajor)" +
//            "from UserCourse cn " +
//            "where cn.user= :user " +
//            "order by cn.course.courseName")
//    List<CourseNameDto> findCourseNameDtoByUser(@Param("user") User user);

    void deleteByUser(User user);

    /**
     * User 자식관계 UserCourse엔티티 삭제하는 메서드입니다.
     * User를 삭제하기 위해 먼저 호출이 됩니다.
     */
    @Modifying
    @Async
    @Transactional
    @Query("DELETE FROM UserCourse uc" +
            " where uc.user.id = :userId")
    CompletableFuture<Void> deleteByUserId(@Param("userId") Long userId);
}
