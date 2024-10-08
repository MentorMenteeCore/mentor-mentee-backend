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

    // 닉네임으로 User 엔티티 조회
    public Optional<User> findByNickName(String nickName) {
        return em.createQuery(
                        "select u from User u where u.nickName = :nickName", User.class)
                .setParameter("nickName", nickName)
                .getResultList()
                .stream()
                .findFirst();
    }

    // User 엔티티를 사용하여 UserCourse를 페이징 처리하여 조회
    public Page<UserCourse> findUserCoursesByUser(User user, Pageable pageable) {
        List<UserCourse> userCourses = em.createQuery(
                        "select uc from UserCourse uc " +
                                "join fetch uc.course c " +
                                "where uc.user = :user",
                        UserCourse.class)
                .setParameter("user", user)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = em.createQuery(
                        "select count(uc) from UserCourse uc where uc.user = :user", Long.class)
                .setParameter("user", user)
                .getSingleResult();

        return new PageImpl<>(userCourses, pageable, total);
    }

    // User 엔티티를 사용하여 AvailableTime 조회
    public List<AvailableTime> findAvailabilitiesByUser(User user) {
        return em.createQuery(
                        "select a from AvailableTime a where a.user = :user", AvailableTime.class)
                .setParameter("user", user)
                .getResultList();
    }

    // User 엔티티를 사용하여 리뷰 조회
    public List<Review> findReviewsByUser(User user) {
        return em.createQuery(
                        "select r from Review r where r.reviewee = :user", Review.class)
                .setParameter("user", user)
                .getResultList();
    }

    // User 엔티티를 사용하여 UserCourse 삭제
    public void deleteUserCoursesByUser(User user) {
        em.createQuery("delete from UserCourse uc where uc.user = :user")
                .setParameter("user", user)
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

    // AvailableTime 저장
    public void saveAvailableTime(AvailableTime availableTime) {
        if (availableTime.getId() == null) {
            em.persist(availableTime);
        } else {
            em.merge(availableTime);
        }
    }

    // UserCourse ID로 UserCourse 레코드 삭제
    public void deleteUserCourseById(Long userCourseId) {
        em.createQuery("delete from UserCourse uc where uc.id = :userCourseId")
                .setParameter("userCourseId", userCourseId)
                .executeUpdate();
    }

    // coursename과 professor로 course 엔티티 조회
    public Course findCourseByNameAndProfessor(String courseName, String professor) {
        return em.createQuery(
                        "select c from Course c where c.courseName = :courseName and c.professor = :professor", Course.class)
                .setParameter("courseName", courseName)
                .setParameter("professor", professor)
                .getSingleResult();
    }
}
