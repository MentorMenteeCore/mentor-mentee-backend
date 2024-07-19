package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter

/**
 * 학부
 */
public class Department {

    @Id
    @GeneratedValue
    @Column(name = "department_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;

    private String departmentName;
}
