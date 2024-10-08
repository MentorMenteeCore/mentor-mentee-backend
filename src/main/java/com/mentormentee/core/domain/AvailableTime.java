package com.mentormentee.core.domain;


import jakarta.persistence.*;
import lombok.Getter;


import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
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


    public AvailableTime(User user, DayOfWeek dayOfWeek, LocalTime availableStartTime, LocalTime availableEndTime) {
        setUserBothWay(user);
        this.dayOfWeek = dayOfWeek;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
    }

    public void setUserBothWay(User user){
        this.user = user;
        user.getAvailabilities().add(this);
    }
}
