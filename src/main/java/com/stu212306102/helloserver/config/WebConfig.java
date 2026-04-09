package com.stu212306102.helloserver.config;

import com.stu212306102.helloserver.interceptor.AuthInterceptor;
import com.stu212306102.helloserver.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/users/login",
                                     "/api/users"); // 只放行登录

        // 拦截器2：你的权限拦截器 AuthInterceptor（所有接口）
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/users/login",
                        "/api/users");
    }
}