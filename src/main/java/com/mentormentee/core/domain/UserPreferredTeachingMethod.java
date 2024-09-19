package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.eclipse.angus.mail.imap.protocol.Item;

import static jakarta.persistence.FetchType.LAZY;

/**
 * User와 PreferredTeachingMethod
 * 이 두 엔티티가 다대 다 관계라서
 * 두 엔티티 사이에 하나 추가하였다.
 */
@Entity
@Getter
public class UserPreferredTeachingMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_Preferred_Teaching_Method_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "preferred_teaching_method_id")
    private PreferredTeachingMethod preferredTeachingMethod;

    public void createUserMethod(User user, PreferredTeachingMethod userTeachingMethod) {

        this.user = user;
        this.preferredTeachingMethod = userTeachingMethod;
    }

}
