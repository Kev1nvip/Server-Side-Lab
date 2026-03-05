package com.stu212306102.helloserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 声明为REST控制器，返回数据而非页面
@RestController
public class HelloController {
    // 定义GET请求接口，路径为/hello
    @GetMapping("/hello")
    public String sayHello() {
        // 接口返回内容，可自定义
        return "Hello Spring Boot 3.5.11! This is a test API.";
    }
}
