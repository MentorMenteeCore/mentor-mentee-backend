package com.mentormentee.core.token.filter.FilterException;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogoutException {

    public static HttpServletResponse logoutExceptionResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = "{\"error\": \"로그아웃된 유저입니다.\"}";
        response.getWriter().write(json);
        return response;
    }

}
