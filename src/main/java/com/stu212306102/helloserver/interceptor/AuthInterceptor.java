package com.stu212306102.helloserver.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("实际请求方法：" + request.getMethod());
        System.out.println("实际请求路径：" + request.getRequestURI());
        // 1. 获取请求方法与路径
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 规则A：POST /api/users → 放行（注册）
        boolean isCreateUser = "POST".equalsIgnoreCase(method)
                && "/api/users".equals(uri);

        // 规则B：GET /api/users/* → 放行（查询）
        boolean isGetUser = "GET".equalsIgnoreCase(method)
                && uri.startsWith("/api/users/");

        // 满足公开接口直接放行
        if (isCreateUser || isGetUser) {
            return true;
        }

        // 2. 敏感操作校验 Token
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            String errorJson = "{\"code\":401,\"msg\":\"非法操作：敏感动作[" + method + "]需登录授权\"}";
            response.getWriter().write(errorJson);
            return false;
        }

        return true;
    }
}