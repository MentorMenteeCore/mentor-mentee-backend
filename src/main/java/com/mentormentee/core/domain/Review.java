package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //리뷰를 받은 멘토
    private int rating;
    private String comment;
    private LocalDate reviewDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mentor_id")
//    private Mentor mentor;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mentee_id")
//    private Mentee mentee;

    public void createDate(int year, int month, int day) {
        reviewDate = LocalDate.of(year, month, day);
    }

    public void createReview( User user, int rating, String comment) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }
}
