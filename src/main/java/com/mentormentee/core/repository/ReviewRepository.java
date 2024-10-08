package com.mentormentee.core.repository;

import com.mentormentee.core.domain.Review;
import com.mentormentee.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select rv from Review rv where rv.user = :user")
    List<Review> findReviewsByMentor(@Param("user") User user);
}
