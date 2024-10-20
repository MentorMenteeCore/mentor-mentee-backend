package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 멘토는 만들지 않으면 누가 누구에게 어떤 리뷰를 남겼는지 표현하기 어려워서 필드가 거의 없음에도 넣긴 함.
 * 이것도 향후 다른 방법이 생기면 없앨 예정
 */
@Entity
@Getter @Setter
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

}
