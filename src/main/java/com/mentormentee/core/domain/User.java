package com.mentormentee.core.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "users")
// builder 패턴 적용을 위한 생성자
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class
User implements UserDetails {

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

    // 리프래시 토큰 필드 추가
    private String refreshToken;

    //유저가 수강하는 과목들 추가.
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserCourse> userCourse = new ArrayList<>();

    //자기소개
    private String selfIntroduction;

    //선호하는 수업 방식
    //해시태그로 여러개 있을 수 있음
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPreferredTeachingMethod> userPreferredTeachingMethodList = new ArrayList<>();

    /**
     * 회원정보 페이지를 보면 학과,학년,이미지를 가져오기 때문에
     * 이를 연관관계로 적는다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;



    public User updatePassword(String newPassword) {
        this.password = newPassword;
        return this;
    }

    // 비밀번호 암호화 로직
    public User hashPassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
        return this;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.userRole.toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void createUser(String userName, String nickName, Role userRole, String email, String password, LocalTime availableStartTime, LocalTime availableEndTime, WaysOfCommunication waysOfCommunication, int yearInUni, String userProfilePicture, Department department,String refreshToken, String selfIntro) {
        this.userName = userName;
        this.userRole = userRole;
        this.email = email;
        this.password = password;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.waysOfCommunication = waysOfCommunication;
        this.nickName = nickName;
        this.userProfilePicture = userProfilePicture;
        this.department = department;
        this.refreshToken = refreshToken;
        this.selfIntroduction = selfIntro;
    }

}
