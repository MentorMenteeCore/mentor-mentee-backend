package com.mentormentee.core.repository;

import com.mentormentee.core.domain.AvailableTime;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MentorDetailsRepository extends JpaRepository<UserCourse, Long> {

    Optional<Object> findUserById(Long userId);

    List<AvailableTime> findAvailabilitiesByUserId(Long userId);

    Collection<Object> findReviewsByUserId(Long userId);

    @EntityGraph(attributePaths = {"course"})
    Page<UserCourse> findUserCourseByUser(User user, Pageable pageable);

    List<AvailableTime> findAvailabilitiesByUser(User user);

    Collection<Object> findReviewsByUser(User user);
}
