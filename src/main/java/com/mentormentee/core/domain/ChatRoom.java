package com.mentormentee.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private LocalDateTime createDate;

    private String roomId;
    private Long firstUserId;
    private Long secondUserId;

    public ChatRoom(LocalDateTime createDate, String roomId, Long firstUserId, Long secondUserId) {
        this.createDate = createDate;
        this.roomId = roomId;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
    }

    public ChatRoom() {
    }

    public static String getRoomId(String userId1, String userId2){
        int firstId = Integer.parseInt(userId1);
        int secondId = Integer.parseInt(userId2);

        if(firstId > secondId){
            return secondId+"/"+firstId;
        }else{
            return firstId+"/"+secondId;
        }
    }

}
