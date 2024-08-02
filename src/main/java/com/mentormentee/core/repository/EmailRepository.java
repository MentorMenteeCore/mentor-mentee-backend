package com.mentormentee.core.repository;


import com.mentormentee.core.domain.EmailSession;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Slf4j
public class EmailRepository {
    private final EntityManager em;

    // 유저 이메일을 이용하여 이메일 세션 조회
    public Optional<EmailSession> findByUserEmail(String email) {
        try {
            EmailSession emailSession = em.createQuery("Select e from email_session e where e.email = :email", EmailSession.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.ofNullable(emailSession);

        }
        catch (Exception e) {
            return Optional.empty();
        }
        // 이메일 세션이 존재하지 않을 수도 있기 때문에 Optional 객체에 담아서 리턴.

    }


    // user의 save와 동일한 로직으로 작성
    public EmailSession save(EmailSession emailSession){
        if (emailSession.getId() == null) {
            em.persist(emailSession);
        }else {
            em.merge(emailSession);
        }
        return emailSession;
    }

    public void delete(EmailSession emailSession){
        em.remove(emailSession);
    }
}
