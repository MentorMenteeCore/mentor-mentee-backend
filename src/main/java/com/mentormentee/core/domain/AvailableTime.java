package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@Setter
public class AvailableTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availabletime_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;  // 요일 저장

    @Column(nullable = false)
    private LocalTime availableStartTime;

    @Column(nullable = false)
    private LocalTime availableEndTime;

    public AvailableTime() {}

    // 필요한 생성자
    public AvailableTime(User user, DayOfWeek dayOfWeek, LocalTime availableStartTime, LocalTime availableEndTime) {
        this.user = user;
        this.dayOfWeek = dayOfWeek;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableTime that = (AvailableTime) o;
        return Objects.equals(user, that.user) &&
                dayOfWeek == that.dayOfWeek &&
                Objects.equals(availableStartTime, that.availableStartTime) &&
                Objects.equals(availableEndTime, that.availableEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, dayOfWeek, availableStartTime, availableEndTime);
    }

}
