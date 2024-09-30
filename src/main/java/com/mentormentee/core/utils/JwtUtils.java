package com.mentormentee.core.utils;



import com.mentormentee.core.exception.exceptionCollection.JWTAuthorizationException;
import com.mentormentee.core.token.dto.AuthToken;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
//토큰 생성 & 토큰에서 email 추출

    private static String JWT_SECRET_KEY;

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 30L; //30본

    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L; //30일

    // setter 주입
    @Value("${jwt.secret}")
    public void setKey(String key){
        JWT_SECRET_KEY = key;
    }

    // 해당 메서드들을 static으로 지정하여 전역적으로 사용할 수 있도록 구현
    public static String generateAccessToken(String email, List<String> roles){
        // 토큰 발급
        String accessToken = Jwts.builder()
                // subject = 토큰 제목으로 주로 식별자를 사용하기 때문에 email로 지정
                .setSubject(email)
                // clain = 내용으로 권한을 삽입했습니다.
                .claim("role", roles)
                // 발급 시간
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 만료 시간
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();

        return accessToken;
    }
    public static String generateRefreshToken(String userId){
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();
    }

    public static AuthToken generateToken(String userId, List<String> roles) {
        return AuthToken.builder()
                .grantType("Bearer")
                .accessToken(JwtUtils.generateAccessToken(userId, roles))
                .refreshToken(JwtUtils.generateRefreshToken(userId))
                .build();
    }


    public static boolean validateToken(String token) throws SecurityException, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
        Jwts.parserBuilder().setSigningKey(JWT_SECRET_KEY).build().parseClaimsJws(token);
        return true;
    }

//
//    public static boolean validateToken(String token) {
//        try{
//            Jwts.parserBuilder().setSigningKey(JWT_SECRET_KEY).build().parseClaimsJws(token);
//            return true;
//        }
//        catch (SecurityException e) {
//            log.info("Invalid JWT Signature");
//            throw e;
//        } catch (MalformedJwtException e) {
//            log.info("Invalid JWT token.");
//            throw e;
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT token.");
//            throw e;
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT token.");
//            throw e;
//        } catch (IllegalArgumentException e) {
//            log.info("JWT token compact of handler are invalid.");
//            throw e;
//        }
//    }

    public static Claims parseClaims(String token){
        try{
            return Jwts.parserBuilder().setSigningKey(JWT_SECRET_KEY).build().parseClaimsJws(token).getBody();
        }
        catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    public static String getUserEmail(){
        String accessToken = JwtUtils.getAccessToken();
        Claims body = JwtUtils.parseClaims(accessToken);
        String email = body.get("sub", String.class);

        return email;
    }

    public static String getAccessToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        else {
            throw new JWTAuthorizationException();
        }

        return token;
    }

    public static String getUserEmailFromRefreshToken(String refreshToken){
        Claims body = JwtUtils.parseClaims(refreshToken);
        String email = body.get("sub", String.class);

        return email;
    }
}
