package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
/**
 * 과목
 */

public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "course")
    private List<UserCourse> userCourse = new ArrayList<>();

    private String courseName;
    private int credit;
    private String professor;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_year")
    private CourseYear courseYear; // 학년 정보
    
    public void createCourse(String courseName, int credit, String professor, CourseYear courseYear, Department department) {
        this.courseName=courseName;
        this.credit=credit;
        this.professor=professor;
        this.courseYear=courseYear;
        this.department=department;
    }
    
    public void changeCourseInfo(String newCourseName, int newCredit) {
        this.courseName = newCourseName;
        this.credit = newCredit;
    }
}

