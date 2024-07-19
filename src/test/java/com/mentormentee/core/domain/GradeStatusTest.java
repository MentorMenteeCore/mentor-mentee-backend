package com.mentormentee.core.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeStatusTest {


    @PersistenceContext
    private EntityManager em;


    /**
     * 이런식으로 APLUS를 A+로 바꿈
     */
    @Test
    @Transactional
    @Rollback(false)
    public void EnumTest() throws Exception {
        //given
        UserCourse userCourse = new UserCourse();
        userCourse.setGradeStatus(GradeStatus.APLUS);
        em.persist(userCourse);

        //when
        em.flush();
        em.clear();

        //then
        Assertions.assertThat(em.find(UserCourse.class, userCourse.getId())).isNotNull();
        Assertions.assertThat(em.find(UserCourse.class, userCourse.getId()).getGradeStatus().getDisplayValue())
                .isEqualTo("A+");



    }

    
}