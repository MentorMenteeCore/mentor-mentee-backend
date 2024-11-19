package com.mentormentee.core.repository;

import com.mentormentee.core.domain.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorListRepository extends JpaRepository<UserCourse, Long>, MentorListRepositoryCustom {
}








