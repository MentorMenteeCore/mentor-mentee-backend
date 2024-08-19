package com.mentormentee.core.repository;


import com.mentormentee.core.domain.Review;
import com.mentormentee.core.domain.UserCourse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MentorDetailsRepository {

    @PersistenceContext
    private EntityManager em;

    //사용자 ID로 UserCourse 조회
    public UserCourse findUserCourseByUserId(Long userId) {
        return em.createQuery(
                        "select uc from UserCourse uc join fetch uc.user u join fetch uc.course c where uc.user.id = :userId",
                        UserCourse.class)
                .setParameter("userId", userId)
                .getSingleResult();


    }


    //사용자 ID로 리뷰 조회
    public List<Review> findReviewsByUserId(Long userId) {
        return em.createQuery(
                        "select r from Review r where r.reviewee.id = :userId", Review.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}

