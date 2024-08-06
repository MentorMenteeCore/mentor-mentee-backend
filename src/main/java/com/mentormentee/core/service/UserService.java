package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.exception.UserNotFoundException;
import com.mentormentee.core.repository.DepartmentRepository;
import com.mentormentee.core.repository.UserRepository;

import com.mentormentee.core.token.dto.AuthToken;
import com.mentormentee.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor//파이널이 있는 필드만 가지고 생성자를 호출해 준다.
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 회원 저장
     * User 객체를 그대로 받는 것은 위험할수도 있기 때문에 dto로 받음
     */
    @Transactional
    public Long save(UserSignUpRequestDto userSignUpRequestDto) {
        String email = userSignUpRequestDto.getEmail();
        String userName = userSignUpRequestDto.getUserName();
        String password = userSignUpRequestDto.getPassword();
        Role role = userSignUpRequestDto.getRole();
        String nickname = userSignUpRequestDto.getNickname();

        // 이메일 중복시 예외
        userRepository.findByEmail(email)
                .ifPresent(x -> {
                    throw new IllegalStateException("이미 존재하는 유저입니다.");
                });

        //의사소통 방식은 Remote를 디폴트로 설정.
        User user = User.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .userRole(role)
                .nickName(nickname)
                .waysOfCommunication(WaysOfCommunication.REMOTE)
                .build();

        user.hashPassword(passwordEncoder);

        return userRepository.save(user);
    }

    public AuthToken login(LoginRequestDto loginRequestDto){
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        // 자격 증명 확인
        // 여기서 CustomUserDetailService의 loadUser메서드가 실행되고 비밀번호 검증까지 완료
        // loadUser메서드는 Authentication객체 실행될때 무조건 실행
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증이 완료되면 해당 authenticate 객체에서 role을 뽑음
        Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // 이후 auth token을 생성
        AuthToken authToken = JwtUtils.generateToken(email, roles);

        // authenticate 객체에 user 객체가 포함되어 있어 이를 뽑음
        User user = (User) authenticate.getPrincipal();

        // 생성된 refresh token을 user.refreshToken 필드에 넣음
        user.setRefreshToken(authToken.getRefreshToken());

        userRepository.save(user);

        return authToken;
    }


    public UserInformDto getUserinformation() {
        // 헤더에서 유저정보를 가져오기 위해 JwtUtils.getUserEmail 사용함
        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("유저가 존재하지 않습니다."));
        UserInformDto userInformDto;

        if(user.getDepartment() == null) {
            userInformDto = new UserInformDto(user.getNickName(), user.getEmail(), null, user.getYearInUni(), user.getUserProfilePicture());
        }
        else{
            userInformDto = new UserInformDto(user.getNickName(), user.getEmail(), user.getDepartment().getDepartmentName(), user.getYearInUni(), user.getUserProfilePicture());
        }
        return userInformDto;

    }

    @Transactional
    public Long updateUserInformationService(UserInformDto userInformation) {
        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("유저가 존재하지 않습니다."));

        if (userInformation.getUserDepartment() != null) {
            Department departmentByName = departmentRepository.findDepartmentByName(userInformation.getUserDepartment());
            user.setDepartment(departmentByName);
        }
        if (userInformation.getUserNickname() != null) {
            user.setNickName(userInformation.getUserNickname());
        }
        if (userInformation.getYearInUni() != user.getYearInUni()) {
            user.setYearInUni(userInformation.getYearInUni());
        }
        if (userInformation.getUserEmail() != null) {
            user.setEmail(userInformation.getUserEmail());
        }
        if (userInformation.getUserImageUrl() != null) {
            user.setUserProfilePicture(userInformation.getUserImageUrl());
        }

        Long save = userRepository.save(user);

        return save;

    }

    /**
     * 유저의 이메일을 통해 유저를 찾고 유저가 존재하면
     * 유저를 삭제
     */
    @Transactional
    public Long deleteUserByEmail() {
        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("유저가 존재하지 않습니다."));
        userRepository.deleteUser(user.getId());
        return user.getId();
    }


}


