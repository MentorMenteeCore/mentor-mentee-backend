package com.mentormentee.core.repository;

import com.mentormentee.core.domain.Review;
import com.mentormentee.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select rv from Review rv where rv.user = :user")
    List<Review> findReviewsByMentor(@Param("user") User user);

    @Modifying
    @Async
    @Transactional
    @Query("delete from Review r where r.user = :user")
    CompletableFuture<Void> deleteReviewsByUser(User user);
}
