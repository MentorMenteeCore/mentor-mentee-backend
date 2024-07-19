package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;


    private String userName;
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role userRole;

    private String email;
    private LocalDateTime availableStartTime;
    private LocalDateTime availableEndTime;  //은채님 이부분 ERD 수정해주세요

    private String password;

    @Enumerated(EnumType.STRING)
    private WaysOfCommunication waysOfCommunication;



}
