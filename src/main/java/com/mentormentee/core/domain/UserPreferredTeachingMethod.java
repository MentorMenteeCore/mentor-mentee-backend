package com.mentormentee.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.eclipse.angus.mail.imap.protocol.Item;

import java.util.Objects;

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

    private String preferredTeachingMethod;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "preferred_teaching_method_id")
//    private PreferredTeachingMethod preferredTeachingMethod;
//
    public void createUserMethod(User user, String userTeachingMethod) {

        this.user = user;
        this.preferredTeachingMethod = userTeachingMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPreferredTeachingMethod that = (UserPreferredTeachingMethod) o;
        return Objects.equals(user, that.user) && Objects.equals(preferredTeachingMethod, that.preferredTeachingMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, preferredTeachingMethod);
    }
}
