package com.mentormentee.core.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로에 대해 CORS 적용
                .allowedOrigins("http://localhost:5173")  // 프론트엔드 출처 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메서드
                .allowedHeaders("Authorization", "Content-Type", "Accept")  // 허용할 헤더들
                .exposedHeaders("Authorization")  // 노출할 응답 헤더 (예: JWT 토큰)
                .allowCredentials(true)  // 쿠키와 인증 정보 허용
                .maxAge(3600);
    }
}