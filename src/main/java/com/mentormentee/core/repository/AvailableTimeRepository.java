package com.mentormentee.core.repository;

import com.mentormentee.core.domain.AvailableTime;
import com.mentormentee.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {
    @Modifying
    @Async
    @Query("delete from AvailableTime a where a.user = :user")
    CompletableFuture<Void> deleteByUser(@Param("user") User user);
}
