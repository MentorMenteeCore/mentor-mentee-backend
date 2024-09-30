package com.mentormentee.core.repository;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserPreferredTeachingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserPreferredTeachingMethodRepository extends JpaRepository<UserPreferredTeachingMethod, Long> {


//    @Query("select new com.mentormentee.core.dto.PreferredTeachingMethodDto(pt.teachingMethod) " +
//            "from UserPreferredTeachingMethod tm " +
//            "left join tm.preferredTeachingMethod pt " +
//            "where tm.user = :user")
//    List<PreferredTeachingMethodDto> findTeachingMethodsByUser(@Param("user") User user);

    void deletePreferredTeachingMethodsByUser(@Param("user") User user);

    List<UserPreferredTeachingMethod> findUserPreferredTeachingMethodByUser(User user);

    @Modifying
    @Transactional
    @Query("delete FROM UserPreferredTeachingMethod upm" +
            " WHERE upm.user.id = :userId")
    void deletePreferredTeachingMethodsByUserId(@Param("userId") Long userId);


}
