package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * 채팅방, 메세지 따로 엔티티 만듦
 */
@Entity
@Getter @Setter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_rooom_id")
    private Long id;

    private LocalDateTime createDate;

    @Enumerated
    private ReadOrNot readOrNot;



}
