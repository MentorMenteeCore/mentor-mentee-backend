package com.mentormentee.core.repository;
import com.mentormentee.core.domain.Review;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.domain.UserCourse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long save(User user) {
        if (user.getId() == null) {
            em.persist(user);
        }else {
            em.merge(user);
        }
        return user.getId();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    /**
     * Join fetch를 함으로써 쿼리 두방 날릴꺼 한방으로 줄임.
     */
    public List<User> findAll() {
        return em.createQuery("select u from User u join fetch u.department d", User.class)
                .getResultList();
    }

    /**
     * 이메일 중복 확인
     * ispresent()사용하기 위해 Optional
     */
    public Optional<User> findByEmail(String email) {
        try{
            User existingUser = em.createQuery("select u from User u " +
                            "left join fetch u.department d " +
                            "where u.email = :email", User.class)
                    .setParameter("email", email).getSingleResult();
            return Optional.ofNullable(existingUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }



    /**
     * 멘토 Id받으면 그 멘토에게 남긴 리뷰들 다 봔환
     */
    public List<Review> findMentorReviewsById(Long mentorId) {
        return em.createQuery("select r from Review r where r.mentor.id = :mentorId", Review.class)
                .setParameter("mentorId", mentorId).getResultList();
    }


    /**
     * 유저 코스를 파라미터로 받으면 그 아이디를 가지고 있는 코스를 DB에서 다 가져온 후
     * 결과를 리스트로 반환
     * @param userCourse 기준이 되는 UserCourse 엔티티
     * @return 해당 코스를 수강하는 유저들의 리스트
     */
    public List<User> findByUserCourse(UserCourse userCourse) {
        List<UserCourse> userCourseList = em.createQuery("select uc from UserCourse uc where uc.id = :userCourseId", UserCourse.class)
                .setParameter("userCourseId", userCourse.getId())
                .getResultList();

        /**
         * 코스를 듣는 유저코스 엔티티 가져온 후 유저만 뽑아서
         * 리스트에 담음
         */
        List<User> usersList = new ArrayList<>();
        for (UserCourse uc : userCourseList) {
            usersList.add(uc.getUser());
        }

        return usersList;
    }

    public Long findByUserEmail(String email) {
            User email1 = em.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return email1.getId();
    }

    public void deleteUser(Long userId) {
            em.createQuery("delete from User u where u.id = :userId").setParameter("userId", userId).executeUpdate();
    }

    /**
     * 닉네임 중복확인을 하기위해
     * 유저가 입력한 닉네임이
     * 디비에 있는지 확인합니다.
     *
     * 없으면 예외가 발생합니다.
     */
    public User getUserByNickname(String nickname){
        return em.createQuery("select u.nickName from User u where u.nickName = :nickname", User.class)
                .setParameter("nickname", nickname)
                .getSingleResult();
    }
}
