package com.mentormentee.core.repository;

import com.mentormentee.core.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MentorDetailsRepository extends JpaRepository<UserCourse, Long> {

    // 닉네임으로 User 엔티티 조회
    @Query("select u from User u where u.nickName = :nickName")
    Optional<User> findUserByNickName(@Param("nickName") String nickName);

    // UserCourse 페이징 조회
    @Query("select uc from UserCourse uc join fetch uc.course c where uc.user = :user")
    Page<UserCourse> findUserCoursesByUser(@Param("user") User user, Pageable pageable);

    // User의 AvailableTime 조회
    @Query("select at from AvailableTime at where at.user = :user")
    List<AvailableTime> findAvailabilitiesByUser(@Param("user") User user);

    // User의 리뷰 조회
    @Query("select r from Review r where r.user = :user")
    List<Review> findReviewsByUser(@Param("user") User user);

    //학과-과목으로 조회
    @Query("select c from Course c where c.department.departmentName = :departmentName and trim(c.courseName) = :courseName")
    Optional<Course> findCourseByDepartmentNameAndCourseName(
            @Param("departmentName") String departmentName,
            @Param("courseName") String courseName
    );


}

