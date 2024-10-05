package com.mentormentee.core.token.service;


import com.mentormentee.core.domain.Role;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.exception.exceptionCollection.JWTClaimException;
import com.mentormentee.core.exception.exceptionCollection.LogoutUserException;
import com.mentormentee.core.repository.UserRepository;
import com.mentormentee.core.token.dto.AuthToken;
import com.mentormentee.core.token.dto.RefreshDto;
import com.mentormentee.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RefreshService {
    private final UserRepository userRepository;

    /**
     * 리프레시 유효성 검사 과정
     * 1. 리프레시 Signature 비교
     * 2. 리프레시로 찾은 유저 DB안에 리프레시 존재해야 함(없으면 로그아웃한 유저)
     *
     * 리프레시 발급 과정
     * 1. 유저의 역할을 업데이트 해서 Access와 Refresh를 만들고
     * 2. Refresh는 DB에 저장하고
     * 3. Response에 Access와 Refresh 담아 보낸다.
     *
     * 2024-10-5
     * 최기연
     */
    public AuthToken refresh(RefreshDto refreshDto){
        String refreshToken = refreshDto.getRefreshToken();

        JwtUtils.validateToken(refreshToken);

        String email = JwtUtils.getUserEmailFromRefreshToken(refreshToken);
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new JWTClaimException());

        String DbRefresh = Optional.ofNullable(findUser.getRefreshToken())
                .orElseThrow(() -> LogoutUserException.EXCEPTION);

        Role role = findUser.getUserRole();
        List<String> roles = new ArrayList<>();
        roles.add(role.toString());

        AuthToken newAuthToken = JwtUtils.generateToken(email, roles);
        findUser.setRefreshToken(newAuthToken.getRefreshToken());

        userRepository.save(findUser);

        return newAuthToken;
    }
}
