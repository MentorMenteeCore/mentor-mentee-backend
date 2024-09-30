package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;
    @Column(name = "department_image_url")
    private String departmentImageUrl;
    private String departmentName;

    public void createDepartment(String departmentName, String departmentImage, College college) {

        this.departmentName = departmentName;
        this.college = college;
        this.departmentImageUrl = departmentImage;

    }

}
