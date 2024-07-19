package com.mentormentee.core.service;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    //회원가입 테스트
    @Test
    public void save() throws Exception {

        User user = new User();
        user.setEmail("test@chungbuk.ac.kr");
        Long saveId = userService.save(user);
        em.flush();
        assertEquals(user, userRepository.findById(saveId));
    }

    //이메일 중복 회원 예외 처리 테스트
    @Test
    public void isEmailDuplicated() throws Exception{

        User user1 = new User();
        user1.setEmail("test@chungbuk.ac.kr");

        User user2 = new User();
        user2.setEmail("test@chungbuk.ac.kr");

        userService.save(user1);

        assertThrows(IllegalStateException.class, () -> {
            userService.save(user2);
        });
    }
}