package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private GradeStatus gradeStatus;

    @Enumerated(EnumType.STRING)
    private IsMajor isMajor;

    public void connectUserAndUsercourse(User user) {
        this.user = user;
        user.getUserCourse().add(this);
    }

    public void createUserCourse(User user, Course course, GradeStatus gradeStatus, IsMajor isMajor) {
        this.user=user;
        this.course=course;
        this.gradeStatus=gradeStatus;
        this.isMajor=isMajor;
    }
}
