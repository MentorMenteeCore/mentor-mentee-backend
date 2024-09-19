package com.mentormentee.core.token.service;


import com.mentormentee.core.domain.Role;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.exception.exceptionCollection.JWTClaimException;
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

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RefreshService {
    private final UserRepository userRepository;

    //refresh 토큰 발급
    public AuthToken refresh(RefreshDto refreshDto){
        String refreshToken = refreshDto.getRefreshToken();

        // early return
        JwtUtils.validateToken(refreshToken);

        String email = JwtUtils.getUserEmailFromRefreshToken(refreshToken);

        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new JWTClaimException());

        Role role = findUser.getUserRole();
        List<String> roles = new ArrayList<>();

        roles.add(role.toString());

        AuthToken newAuthToken = JwtUtils.generateToken(email, roles);
        findUser.setRefreshToken(newAuthToken.getRefreshToken());

        userRepository.save(findUser);

        return newAuthToken;
    }
}
