package com.mentormentee.core.service;

import com.mentormentee.core.domain.EmailSession;
import com.mentormentee.core.dto.EmailSendRequestDto;
import com.mentormentee.core.dto.EmailSendResponseDto;
import com.mentormentee.core.dto.EmailVerifyRequestDto;
import com.mentormentee.core.repository.EmailRepository;
import com.mentormentee.core.utils.EmailSendUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmailService {

    private final EmailRepository emailRepository;
    //이메일 보낼 수 있도록 정의
    private final EmailSendUtil emailSendUtil;

    // 인증시간 3분
    private final Long AUTH_CODE_EXPIRATION_TIME = 3 * 60 * 1000L;
    public EmailSendResponseDto sendEmail(EmailSendRequestDto emailSendRequestDto){
        String email = emailSendRequestDto.getEmail();
        String code = createCode();
        Optional<EmailSession> emailSessionOptional = emailRepository.findByUserEmail(email);
        LocalDateTime now = LocalDateTime.now();

        // 이메일 발송을 먼저 한 뒤 db에 저장
        String title = "이메일 인증";
        String body = "인증 번호 = [" + code + "]";
        emailSendUtil.sendEmail(email, title, body);

        // 이메일 세션에 이미 인증번호가 존재하는 경우 -> 재요청을 한 경우
        if(emailSessionOptional.isPresent()){
            // 해당 이메일 세션의 코드와 생성시간을 업데이트한뒤에 저장
            EmailSession emailSession = emailSessionOptional.get();
            emailSession.setVerifyCode(code, now);
            emailRepository.save(emailSession);
        }

        // 새로운 요청이 들어온 경우
        else{

            // 새로운 이메일 세션을 만들고 저장
            EmailSession emailSession = EmailSession.builder()
                    .createdAt(now)
                    .verifyCode(code)
                    .email(email)
                    .build();
            emailRepository.save(emailSession);
        }


        return EmailSendResponseDto.builder()
                .code(code)
                .email(email)
                .createdAt(now)
                .build();
    }


    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();

        } catch (NoSuchAlgorithmException e) {
            log.error("emailService.createCode() exception occur");
            throw new RuntimeException();
        }
    }

    public boolean verifiedCode(EmailVerifyRequestDto emailVerifyRequestDto){
        String email = emailVerifyRequestDto.getEmail();
        String code = emailVerifyRequestDto.getCode();
        LocalDateTime now = LocalDateTime.now();

        // 먼저 이메일 세션에 존재하는 이메일을 찾는다
        EmailSession authInfo = emailRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Email Session Not Found."));

        String AuthCode = authInfo.getVerifyCode();
        LocalDateTime createdAt = authInfo.getCreatedAt();
        Duration duration = Duration.between(createdAt, now);

        boolean authResult = (duration.toMillis() <= AUTH_CODE_EXPIRATION_TIME) && AuthCode.equals(code);

        if(authResult == true){
            // auth 성공하면 기존에 있던 세션은 지우고 true리턴
            emailRepository.delete(authInfo);
            return true;
        }

        return false;
    }
}
