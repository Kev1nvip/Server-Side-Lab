package com.stu212306102.helloserver.controller;

import com.stu212306102.helloserver.common.Result;  // 引入统一响应体
import com.stu212306102.helloserver.entity.User;
import org.springframework.web.bind.annotation.*;

import com.stu212306102.helloserver.dto.UserDTO;
import com.stu212306102.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {


//    // 2. 新增：添加用户，POST请求，接收JSON格式的User对象
//    @PostMapping
//    public Result<String> createUser(@RequestBody User user) {
//        String data = "新增成功,接收到用户:" + user.getName() + ",年龄:" + user.getAge();
//        return Result.success(data);
//    }
//
//    // 3. 修改：全量更新用户信息，PUT请求，路径参数id+JSON请求体
//    @PutMapping("/{id}")
//    public Result<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
//        String data = "更新成功,ID" + id + "的用户已修改为:" + user.getName();
//        return Result.success(data);
//    }

//    // 4. 删除：根据ID删除用户，DELETE请求，路径参数id
//    @DeleteMapping("/{id}")
//    public Result<String> deleteUser(@PathVariable("id") Long id) {
//        String data = "删除成功,已移除ID为" + id + "的用户";
//        return Result.success(data);
//    }

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
        String data = "查询成功，正在返回ID为" + id + "的用户信息";
        return Result.success(data);
    }
}