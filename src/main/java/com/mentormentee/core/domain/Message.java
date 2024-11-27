package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter @Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    //보낸사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User user;
    private String content;
    private LocalDateTime time;
    //상대방이 읽었는지 안읽었는지
    private boolean readOrNot;

    public Message createMessage(String message, ChatRoom room, LocalDateTime now, boolean isUserInRoom, User user) {
        this.content = message;
        this.time = now;
        this.readOrNot = isUserInRoom;
        this.chatRoom = room;
        this.user = user;
        return this;
    }

}
