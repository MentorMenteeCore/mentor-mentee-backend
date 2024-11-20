package com.mentormentee.core.config.interceptor;

import com.mentormentee.core.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Map;

public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //파라미터에서 토큰 가져오고 검증 진행 한다.
        URI uri = request.getURI();
        String[] split = uri.getQuery().split("=");
        String webSocketToken = split[1];
        JwtUtils.validateToken(webSocketToken);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
