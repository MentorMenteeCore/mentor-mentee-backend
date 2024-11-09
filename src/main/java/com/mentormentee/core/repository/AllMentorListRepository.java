package com.mentormentee.core.repository;

import com.mentormentee.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllMentorListRepository extends JpaRepository<User, Long>, AllMentorListRepositoryCustom {
}
