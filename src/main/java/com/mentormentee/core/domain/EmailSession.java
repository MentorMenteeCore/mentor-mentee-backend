package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


// 이메일 인증 번호를 저장하기 위한 테이블
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "email_session")
public class EmailSession {
    @Id
    @Column(name="SESSION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 인증 번호는 6자리로 지정
    @Column(name="verify_code", columnDefinition = "varchar", length = 6,nullable = false)
    private String verifyCode;


    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="email")
    private String email;

    public void setVerifyCode(String verifyCode, LocalDateTime createdAt) {
        this.verifyCode = verifyCode;
        this.createdAt = createdAt;
    }
}
