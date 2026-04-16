package com.stu212306102.helloserver.controller;

import com.stu212306102.helloserver.common.Result;  // 引入统一响应体
import com.stu212306102.helloserver.entity.User;
import org.springframework.web.bind.annotation.*;

import com.stu212306102.helloserver.dto.UserDTO;
import com.stu212306102.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stu212306102.helloserver.vo.UserDetailVO;
import com.stu212306102.helloserver.entity.UserInfo;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // 依赖注入 UserService 业务接口
    @Autowired
    private UserService userService;

    // 1. 新增用户（注册）- 路径为 POST /api/users
    @PostMapping
    public Result<String> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    // 2. 用户登录 - 路径为 POST /api/users/login
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    // 1. 查询：根据ID获取用户信息，GET请求，路径参数id
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    // 查询详情（多表 + Redis）
    @GetMapping("/{id}/detail")
    public Result<UserDetailVO> getUserDetail(@PathVariable("id") Long userId) {
        return userService.getUserDetail(userId);
    }

    // 更新详情
    @PutMapping("/{id}/detail")
    public Result<String> updateUserInfo(@PathVariable("id") Long userId, @RequestBody UserInfo userInfo) {
        userInfo.setUserId(userId);
        return userService.updateUserInfo(userInfo);
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    // 分页查询接口
    @GetMapping("/page")
    public Result<Object> getUserPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return userService.getUserPage(pageNum, pageSize);
    }
}