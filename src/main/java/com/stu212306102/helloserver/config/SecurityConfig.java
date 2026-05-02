package com.stu212306102.helloserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 开启CORS
                .cors(Customizer.withDefaults())

                // 2. 关闭CSRF（前后端分离必须关）
                .csrf(AbstractHttpConfigurer::disable)

                // 3. 无状态：不使用Session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. 接口权限规则
                .authorizeHttpRequests(auth -> auth
                        // 放行注册
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        // 放行登录
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        // 其他所有接口必须认证
                        .anyRequest().authenticated()
                )

                // 5. 关闭默认登录页面
                .formLogin(AbstractHttpConfigurer::disable)
                // 6. 关闭httpBasic弹窗
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}