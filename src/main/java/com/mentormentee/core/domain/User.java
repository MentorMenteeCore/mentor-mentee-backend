package com.mentormentee.core.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Clob;
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

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private WaysOfCommunication waysOfCommunication;

    private int yearInUni;

    @Value("${spring.defaultProfileImage}")
    @Column(name = "profile_url")
    private String profileUrl = "${spring.defaultProfileImage}";



    // 리프래시 토큰 필드 추가
    private String refreshToken;

    //유저가 수강하는 과목들 추가.
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<UserCourse> userCourse = new ArrayList<>();

    //자기소개
    @Lob
    private String selfIntroduction;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableTime> availabilities = new ArrayList<>(); // 사용자의 요일별 시간 리스트


    //선호하는 수업 방식
    //해시태그로 여러개 있을 수 있음
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPreferredTeachingMethod> userPreferredTeachingMethodList = new ArrayList<>();


    public void changeWaysOfCommunication(WaysOfCommunication newWaysOfCommunication) {
        this.waysOfCommunication = newWaysOfCommunication;
    }

    public void changeSelfIntroduction(String newSelfIntroduction) {
        this.selfIntroduction = newSelfIntroduction;
    }

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

    public void createUser(String userName, String nickName, Role userRole, String email, String password, WaysOfCommunication waysOfCommunication, int yearInUni, String userProfilePicture, Department department,String refreshToken, String selfIntro) {
        this.userName = userName;
        this.userRole = userRole;
        this.email = email;
        this.password = password;
        this.waysOfCommunication = waysOfCommunication;
        this.nickName = nickName;
        this.profileUrl = userProfilePicture;
        this.department = department;
        this.refreshToken = refreshToken;
        this.selfIntroduction = selfIntro;
        this.yearInUni = yearInUni;
    }

    public void changeRole() {
        if(userRole.equals(Role.ROLE_MENTEE)){
            this.userRole = Role.ROLE_MENTOR;
        }else {
            this.userRole = Role.ROLE_MENTEE;
        }
    }
}

