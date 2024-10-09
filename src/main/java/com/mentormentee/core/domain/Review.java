package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter @Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")  //리뷰를 남긴 사람
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")  //리뷰를 받는 사람
    private User reviewee;

    private int rating;
    private String comment;
    private LocalDateTime reviewDate;


    public void createReview(int rating, LocalDateTime reviewDate, User reviewer, User reviewee, String comment) {
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.comment = comment;
    }

}
