package com.mentormentee.core.repository;

import com.mentormentee.core.domain.Mentor;
import com.mentormentee.core.domain.Review;
import com.mentormentee.core.domain.Role;
import com.mentormentee.core.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    /*
     * 이건 JPA가 내가 의도한 대로 한 멘토의 리뷰들만 가져 오는지 확인하는 테스트 코드입니다.
    */

    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void getReviewList() throws Exception {

        User user = new User();
        user.setUserName("기연");
        user.setEmail("hello@mentormentee.com");
        user.setUserRole(Role.ROLE_MENTOR);

        User user1 = new User();
        user1.setUserName("나멘티");
        user1.setEmail("hello@mentee.com");
        user1.setUserRole(Role.ROLE_MENTEE);

        User user2 = new User();
        user1.setUserName("악플맨");
        user1.setEmail("hello@badguy.com");
        user1.setUserRole(Role.ROLE_MENTEE);
        em.persist(user);
        em.persist(user1);
        em.persist(user2);

        Mentor mentor = new Mentor();
        mentor.setUser(user);
        em.persist(mentor);

        Review review1 = new Review();
        review1.setUser(user1);
        review1.setRating(4);
        review1.setMentor(mentor);
        review1.setComment("꿀팁 많이 알려주심");

        Review review2 = new Review();
        review2.setUser(user2);
        review2.setRating(2);
        review2.setMentor(mentor);
        review2.setComment("아 돈 날림");
        em.persist(review1);
        em.persist(review2);
        em.flush();
        em.clear();

        List<Review> mentorReviewsById = userRepository.findMentorReviewsById(mentor.getId());

        // 각 리뷰의 ID와 코멘트를 출력
        for (Review review : mentorReviewsById) {
            System.out.println("Review Rating: " + review.getRating() + ", Comment: " + review.getComment());
        }
    }



}