package com.stu212306102.helloserver.interceptor;

import com.stu212306102.helloserver.common.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component // 建议加上，让 Spring 管理
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 打印一下，方便调试
        System.out.println("拦截器正在校验路径: " + request.getRequestURI());

        // 1. 从 Header 获取 Token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return sendError(response, "未登录或Token无效");
        }

        // 2. 校验 Token
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtil.validateToken(token);
        if (username == null) {
            return sendError(response, "Token 无效或已过期");
        }

        return true;
    }

    private boolean sendError(HttpServletResponse response, String msg) throws Exception {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"msg\":\"" + msg + "\"}");
        return false;
    }
}