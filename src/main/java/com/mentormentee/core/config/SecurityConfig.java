package com.mentormentee.core.config;

import com.mentormentee.core.service.CustomUserDetailService;
import com.mentormentee.core.token.filter.JwtFilter;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;

    /**
     * 가독성과 유지 보수성을 높이기 위해 따로 URL을 배열로 뽑음
     * 아래의 url은 로그인이 필요없는 url이다.
     */
    private static final String[] PERMIT_URLS = {   "/api/user/sign-up",
                                                    "/api/user/login",
                                                    "/api/email/**",
                                                    "/api/refresh",
                                                    "/swagger-ui/**",
                                                    "/v3/api-docs/**",
                                                    "/api/college/*",
                                                    "/api/search"
                                                                        };

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {//cross origin문제 해결
//
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                        CorsConfiguration config = new CorsConfiguration();
//                        config.setAllowCredentials(true);//인증정보 받을건지
//                        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));//상대방 url
//                        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));//어떤 졸휴의 http 헤더 받을건지 . *이면 다 받을 수 있다는거
//                        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));// get , post, patch 뭐 받을건지. *이면 다 받을 수 있다는거
//                        config.setExposedHeaders(Arrays.asList("Authorization"));// 우리가 JWT 토큰을 해더에 담아서 보낼때 그 해더 이름을 적음으로써 브라우저에서 볼 수 있게 해달라는 것이다.
//                        config.setMaxAge(3600L);
//                        return config;
//                    }
//
//                }))
                // username, password 헤더 로그인 방식 해제 (BasicAuthenticationFilter 비활성화)
                .httpBasic(AbstractHttpConfigurer::disable)
                // csrf 에러 방지
                .csrf(AbstractHttpConfigurer::disable)
                // 인증 url 설정
                .authorizeHttpRequests(authorize -> authorize
                        // 유저 로그인 이전에 이루어지는 요청 (회원가입, 로그인, 이메일 발송, 처음 학과 나열 페이지) 등과 같은 api는 인증하지 않기 위해 permitAll 적용
                        .requestMatchers(PERMIT_URLS).permitAll()
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
