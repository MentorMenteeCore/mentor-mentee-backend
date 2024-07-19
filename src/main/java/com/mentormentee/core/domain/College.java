package com.mentormentee.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter

/**
 * 단과대
 */
public class College {

    @Id
    @GeneratedValue
    @Column(name = "college_id")
    private Long id;
    private String collegeName;

}
