package com.mentormentee.core.repository;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserPreferredTeachingMethod;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 언제 사용? : 유저 엔티티를 가지고 유저가 선호하는
 *            수업 방식들을 가져오고 싶을때
 */
@Repository
public interface PreferredTeachingMethodRepository extends JpaRepository<UserPreferredTeachingMethod, Long> {

    @EntityGraph(attributePaths = "preferredTeachingMethod")
    @Query("select p.teachingMethod as teachingMethod" +
            " from UserPreferredTeachingMethod u" +
            " join u.preferredTeachingMethod p" +
            " where u.user= :user")
    List<PreferredTeachingMethodOnly> findUserPreferredTeachingMethodByUser(@Param("user") User user);

}
