package com.mentormentee.core.token.filter;


import com.mentormentee.core.service.CustomUserDetailService;
import com.mentormentee.core.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class
JwtFilter extends OncePerRequestFilter {
    
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);


            // 여기서 http header에 들어가 있는 토큰을 검증하고 사용자가 인증된 사용자면
            // 스프링 내부에 있는 security context holder 세션에 삽입
            // 이를 이용해서 api를 사용하고, 유저 정보를 가져올 수 있다
        try{
            if (token != null && JwtUtils.validateToken(token)) {
                Authentication authentication = this.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e){
            log.error(e.getMessage());
            handleException(response, e);
        }

    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String message;
        if (e instanceof ExpiredJwtException) {
            message = "Expired JWT token.";
        } else if (e instanceof MalformedJwtException) {
            message = "Invalid JWT token.";
        } else if (e instanceof UnsupportedJwtException) {
            message = "Unsupported JWT token.";
        } else if (e instanceof IllegalArgumentException) {
            message = "JWT token compact of handler are invalid.";
        } else {
            message = "Invalid JWT signature.";
        }

        String json = String.format("{\"error\": \"%s\"}", message);
        response.getWriter().write(json);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        // jwt 인증 미 실시 api 보통은 security의 permit all url과 동일하게 간다
        String[] excludePath = {"/api/user/sign-up", "/api/user/login", "/api/email/**", "/api/refresh","/swagger-ui/**", "/v3/api-docs/**","/api/college/*"};

        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    // 헤더에 있는 토큰의 기본 형태가 Authorization : Bearer [access token] 이기 때문에 원문만 뽑아오는 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //Authentication 객체 지금 가져와서 나중에 사용할 것
    private Authentication getAuthentication(String accessToken) {
        // 여기에서 authentication 객체를 뽑아내서 리턴, 이후 위에 doFilterInternal 메서드에서
        // 해당 유저 정보를 security context holder에 넣는다
        Claims claims = JwtUtils.parseClaims(accessToken);
        String userId = claims.getSubject();//userEmail

        //여기서 토큰 에서 추출한 이메일이 db에 있는지 확인
        UserDetails userDetails = customUserDetailService.loadUserByUsername(userId);
        String findUserId = userDetails.getUsername();
        UserDetails principal = new User(findUserId , "", userDetails.getAuthorities());//유저의 이름과 유저의 역할 담아서
        return new UsernamePasswordAuthenticationToken(principal, "", userDetails.getAuthorities());
    }
}
