package com.mentormentee.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter @Setter
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_course_id")
    private Long id;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private GradeStatus gradeStatus;


    public UserCourse() {}
    public UserCourse(User user, Course course,GradeStatus gradeStatus) {
        this.user = user;
        this.course = course;
        this.gradeStatus = gradeStatus;
    }

    public void changeInfo(GradeStatus newGradeStatus) {
        this.gradeStatus = newGradeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCourse that = (UserCourse) o;
        return Objects.equals(course, that.course) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, user);
    }

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
