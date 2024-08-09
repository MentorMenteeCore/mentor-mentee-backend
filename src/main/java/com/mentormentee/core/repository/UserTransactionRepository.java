package com.mentormentee.core.repository;

import com.mentormentee.core.domain.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {

    
    //entitygrpah 어노테이션 사용
    @Query("select ut from UserTransaction ut join fetch ut.transaction where ut.user.id = :userId")
    List<UserTransaction> findByUser(@Param("userId") Long userId);
}



