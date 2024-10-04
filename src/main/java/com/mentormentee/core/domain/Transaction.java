package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;
    private LocalDateTime dateTime;
    private int transactionAmount;//거래 횟수
    private int cieatStock;//내가 보유한 씨앗 양 잔액
}
