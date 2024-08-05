package com.mentormentee.core.domain;

<<<<<<< HEAD
import com.mentormentee.core.dto.UserInformDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
=======

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
>>>>>>> origin/rapgodd-login-joining-verifying-with-email

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
<<<<<<< HEAD
=======

>>>>>>> origin/rapgodd-login-joining-verifying-with-email
    private LocalTime availableStartTime;
    private LocalTime availableEndTime;

    private String password;

    @Enumerated(EnumType.STRING)
    private WaysOfCommunication waysOfCommunication;

    private int yearInUni;
    private String userProfilePicture;

<<<<<<< HEAD
=======
    // 리프래시 토큰 필드 추가
    private String refreshToken;

>>>>>>> origin/rapgodd-login-joining-verifying-with-email
    /**
     * 회원정보 페이지를 보면 학과,학년,이미지를 가져오기 때문에
     * 이를 연관관계로 적는다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
<<<<<<< HEAD
=======


//    public void changeDepartment(UserInformDto userDepartment) {
//        this.department.getDepartmentName() = userDepartment.getUserDepartment();
//    }

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
>>>>>>> origin/rapgodd-login-joining-verifying-with-email


//    public void changeDepartment(UserInformDto userDepartment) {
//        this.department.getDepartmentName() = userDepartment.getUserDepartment();
//    }
}
