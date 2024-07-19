package com.mentormentee.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name = "transaction_id")
    private Long id;
    private LocalDateTime dateTime;
    private int transactionAmount;
    private int cieatStock;
}
