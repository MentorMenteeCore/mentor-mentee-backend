package com.mentormentee.core.repository;

import com.mentormentee.core.domain.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {

    //transaction 내역 가져오기
    @Query("select ut from UserTransaction ut join ut.transaction t where ut.user.id = :userId")
    List<UserTransaction> findByUser(@Param("userId") Long userId);
}


