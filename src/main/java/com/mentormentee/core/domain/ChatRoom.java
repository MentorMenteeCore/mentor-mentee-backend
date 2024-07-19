package com.mentormentee.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * 채팅방, 메세지 따로 엔티티 만듦
 * Setter는 지금은 열어둬서 테스트하고 나중에 닫으려고 하는데 괜찮은지
 */
@Entity
@Getter @Setter
public class ChatRoom {

    @Id
    @GeneratedValue
    @Column(name = "chat_rooom_id")
    private Long id;

    private LocalDateTime createDate;



}
