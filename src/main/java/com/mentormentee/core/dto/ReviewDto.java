package com.mentormentee.core.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReviewDto {
    private String comment;
    private int rating;
    private LocalDateTime reviewDate;

    public ReviewDto(String comment, int rating, LocalDateTime reviewDate) {
        this.comment = comment;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }
}

