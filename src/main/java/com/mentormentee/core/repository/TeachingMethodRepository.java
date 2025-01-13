package com.mentormentee.core.repository;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserPreferredTeachingMethod;
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
public interface TeachingMethodRepository extends JpaRepository<UserPreferredTeachingMethod, Long> {

    void deletePreferredTeachingMethodsByUser(@Param("user") User user);

    List<UserPreferredTeachingMethod> findUserPreferredTeachingMethodByUser(User user);

    @Modifying
    @Async
    @Transactional
    @Query("delete FROM UserPreferredTeachingMethod upm" +
            " WHERE upm.user.id = :userId")
    CompletableFuture<Void> deletePreferredTeachingMethodsByUserId(@Param("userId") Long userId);

}
