package com.mentormentee.core.config;

import com.mentormentee.core.service.CustomUserDetailService;
import com.mentormentee.core.token.filter.JwtFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // username, password 헤더 로그인 방식 해제 (BasicAuthenticationFilter 비활성화)
                .httpBasic(AbstractHttpConfigurer::disable)
                // csrf 에러 방지
                .csrf(AbstractHttpConfigurer::disable)
                // 인증 url 설정
                .authorizeHttpRequests(authorize -> authorize
                        // 유저 로그인 이전에 이루어지는 요청 (회원가입, 로그인, 이메일 발송, 처음 학과 나열 페이지) 등과 같은 api는 인증하지 않기 위해 permitAll 적용
                        .requestMatchers("/api/user/sign-up", "/api/user/login", "/api/email/**", "/api/refresh", "/swagger-ui/**", "/v3/api-docs/**", "api/college/*").permitAll()
                        .anyRequest().authenticated()

                )
                //필터체인의 전역 예외처리. 토큰이 없거나 인가권한 없는 사용자가 오면 예외 발생.
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(unauthorizedEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .addFilterBefore(new JwtFilter(customUserDetailService), UsernamePasswordAuthenticationFilter.class);
        //JwtFilters는 UsernamePasswordAuthenticationFilter를 커스텀하는 것이 아니라 추가하는 것이다. 따라서 addFilterBefore


        return httpSecurity.build();

    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = "{\"error\": \"Unauthorized\"}";
            response.getWriter().write(json);
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = "{\"error\": \"Forbidden\"}";
            response.getWriter().write(json);
        };
    }

}
