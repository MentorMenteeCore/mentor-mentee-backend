package com.mentormentee.core.domain;

import com.mentormentee.core.dto.UserInformDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String userName;
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role userRole;

    private String email;
    private LocalTime availableStartTime;
    private LocalTime availableEndTime;

    private String password;

    @Enumerated(EnumType.STRING)
    private WaysOfCommunication waysOfCommunication;

    private int yearInUni;
    private String userProfilePicture;

    /**
     * 회원정보 페이지를 보면 학과,학년,이미지를 가져오기 때문에
     * 이를 연관관계로 적는다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;


//    public void changeDepartment(UserInformDto userDepartment) {
//        this.department.getDepartmentName() = userDepartment.getUserDepartment();
//    }
}
