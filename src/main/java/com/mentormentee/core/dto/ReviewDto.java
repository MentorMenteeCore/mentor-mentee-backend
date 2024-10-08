package com.mentormentee.core.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDto {
    private String comment;
    private int rate;
    private LocalDate reviewDate;

    public ReviewDto(String comment, int rate, LocalDate reviewDate) {
        this.comment = comment;
        this.rate = rate;
        this.reviewDate = reviewDate;

    }
}
