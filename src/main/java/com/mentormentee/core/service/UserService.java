package com.mentormentee.core.service;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.exception.exceptionCollection.JWTClaimException;
import com.mentormentee.core.repository.*;

//import com.mentormentee.core.repository.PreferredTeachingMethodRepository;
import com.mentormentee.core.token.dto.AuthToken;
import com.mentormentee.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor//파이널이 있는 필드만 가지고 생성자를 호출해 준다.
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    //private final PreferredTeachingMethodRepository preferredTeachingMethodRepository;
    private final MenteeCoursesRepository menteeCoursesRepository;
    private final S3Uploader s3Uploader;

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

        /**
         * 유저 비번이 암호화 되지 않은 비번일때 이거를 암호화
         */
        user.hashPassword(passwordEncoder);//이렇게 하면 비밀번호를 암호화 한다.

        return userRepository.save(user);
    }

    @Transactional
    public AuthToken login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        // 자격 증명 확인
        // 여기서 CustomUserDetailService의 loadUser메서드가 실행되고 비밀번호 검증까지 완료
        // Authentication Manager로 Authentication Token넘겨서 인증 수행.
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

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

    // 프로필 이미지 업로드 메서드 추가
    @Transactional
    public String uploadProfileImage(MultipartFile file) throws IOException {
        return s3Uploader.uploadProfileImage(file);
    }

    // 프로필 이미지 삭제 메서드 추가
    @Transactional
    public String deleteProfileImage() {
        return s3Uploader.deleteProfileImage();
    }


    @Transactional(readOnly = true)
    public UserInformDto getUserinformation() {
        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("유저가 존재하지 않습니다."));


        UserInformDto userInformDto;
        if (user.getDepartment() == null) {
            userInformDto = new UserInformDto(user.getNickName(), user.getEmail(), null, user.getYearInUni(), user.getProfileUrl());
        } else {
            userInformDto = new UserInformDto(user.getNickName(), user.getEmail(), user.getDepartment().getDepartmentName(), user.getYearInUni(), user.getProfileUrl());
        }
        return userInformDto;
    }


    @Transactional
    public Long updateUserInformationService(UserInformDto userInformation) {
        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new JWTClaimException());

        // 부서 정보 수정
        if (userInformation.getUserDepartment() != null) {
            Department departmentByName = departmentRepository.findDepartmentByName(userInformation.getUserDepartment());
            user.setDepartment(departmentByName);
        }
        // 닉네임 수정
        if (userInformation.getUserNickname() != null) {
            user.setNickName(userInformation.getUserNickname());
        }
        // 학년 수정
        if (userInformation.getYearInUni() != user.getYearInUni()) {
            user.setYearInUni(userInformation.getYearInUni());
        }
        // 이메일 수정
        if (userInformation.getUserEmail() != null) {
            user.setEmail(userInformation.getUserEmail());
        }
        // 프로필 이미지 URL 수정
        if (userInformation.getProfileUrl() != null) {
            user.setProfileUrl(userInformation.getProfileUrl());
        }

        return user.getId();
    }


    //       Long save = userRepository.save(user);


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

    /**
     * 유저 비밀번호 변경을 위한 프로세스
     * 1. 기존비밀번호랑 입력비밀번호가 같은지 확인
     * 2. 변경할 비밀번호랑 한번 더 입력한 비밀번호가 같은지 확인
     * 3. 1번과 2번을 통과하면 userRepository에서 변경된 비번과 함께 save 하면 된다.
     */
    @Transactional
    public void updatePassword(PasswordUpdateDto passwordUpdateDto) {

        String userEmail = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("유저 존재하지 않음"));

        String newPassword = passwordUpdateDto.getNewPassword();
        String confirmPassword = passwordUpdateDto.getConfirmPassword();
        String oldPassword = passwordUpdateDto.getOldPassword();

        // 1. 기존 비밀번호롸 입력 비밀번호가 같은지 확인.
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("입력한 비밀번호가 기존 비밀번호와 같지 않습니다.");
        }

        // 2. 새로운 비밀번호와 한번더 비밀번호가 같은지 확인.
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }
        //3. 변경할 비밀번호가 기존 비밀번호랑 같은 경우.
        if (newPassword.equals(oldPassword)) {
            throw new IllegalArgumentException("기존 비밀번호랑 다른 비밀번호를 입력해 주세요.");
        }

        // 1번 2번 3번 통과 -> 저장(merge)
        User newPasswordUser = user.updatePassword(newPassword);
        newPasswordUser.hashPassword(passwordEncoder);
        userRepository.save(newPasswordUser);

    }
}




