package com.mentormentee.core.repository;


import com.mentormentee.core.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class MentorDetailsRepository {

    @PersistenceContext
    private EntityManager em;

    // 사용자 ID로 UserCourse를 페이징 처리하여 조회
    public Page<UserCourse> findUserCoursesByUserId(Long userId, Pageable pageable) {
        List<UserCourse> userCourses = em.createQuery(
                        "select uc from UserCourse uc " +
                                "join fetch uc.user u " +
                                "join fetch uc.course c " +
                                "where uc.user.id = :userId",
                        UserCourse.class)
                .setParameter("userId", userId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = em.createQuery(
                        "select count(uc) from UserCourse uc where uc.user.id = :userId", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();

        return new PageImpl<>(userCourses, pageable, total);
    }

    // 사용자 ID로 User 조회
    public Optional<User> findUserById(Long userId) {
        return em.createQuery("select u from User u where u.id = :userId", User.class)
                .setParameter("userId", userId)
                .getResultStream()
                .findFirst();
    }

    // 사용자의 리뷰 조회
    public List<Review> findReviewsByUserId(Long userId) {
        return em.createQuery(
                        "select r from Review r where r.reviewee.id = :userId", Review.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // 사용자 ID로 AvailableTime 조회
    public List<AvailableTime> findAvailabilitiesByUserId(Long userId) {
        return em.createQuery(
                        "select a from AvailableTime a where a.user.id = :userId", AvailableTime.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // 사용자 ID로 AvailableTime 삭제
    public void deleteAvailableTimesByUserId(Long userId) {
        em.createQuery("delete from AvailableTime a where a.user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    // AvailableTime 저장
    public void saveAvailableTime(AvailableTime availableTime) {
        if (availableTime.getId() == null) {
            em.persist(availableTime);
        } else {
            em.merge(availableTime);
        }
    }

    // 사용자 ID로 UserCourse 삭제
    public void deleteUserCoursesByUserId(Long userId) {
        em.createQuery("delete from UserCourse uc where uc.user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    // UserCourse 저장
    public void saveUserCourse(UserCourse userCourse) {
        if (userCourse.getId() == null) {
            em.persist(userCourse);
        } else {
            em.merge(userCourse);
        }
    }

    //coursename과 professor로 course 엔티티 조회
    public Course findCourseByNameAndProfessor(String courseName, String professor) {
        return em.createQuery(
                        "select c from Course c where c.courseName = :courseName and c.professor = :professor", Course.class)
                .setParameter("courseName", courseName)
                .setParameter("professor", professor)
                .getSingleResult();
    }


    //UserCourseID로 UserCourse 레코드 삭제
    public void deleteUserCourseById(Long userCourseId) {
        em.createQuery("delete from UserCourse uc where uc.id = :userCourseId")
                .setParameter("userCourseId", userCourseId)
                .executeUpdate();
    }

}





