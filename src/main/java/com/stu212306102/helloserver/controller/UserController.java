package com.stu212306102.helloserver.controller;

import com.stu212306102.helloserver.common.Result;  // 引入统一响应体
import com.stu212306102.helloserver.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // 1. 查询：根据ID获取用户信息，GET请求，路径参数id
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        int a = 1 / 0;
        // 使用Result.success()包装返回结果
        return Result.success("查询成功,正在返回ID为" + id + "的用户信息");
    }

    // 2. 新增：添加用户，POST请求，接收JSON格式的User对象
    @PostMapping
    public Result<String> createUser(@RequestBody User user) {
        return Result.success("新增成功,接收到用户:" + user.getName() + ",年龄:" + user.getAge());
    }

    // 3. 修改：全量更新用户信息，PUT请求，路径参数id+JSON请求体
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return Result.success("更新成功,ID" + id + "的用户已修改为:" + user.getName());
    }

    // 4. 删除：根据ID删除用户，DELETE请求，路径参数id
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        return Result.success("删除成功,已移除ID为" + id + "的用户");
    }
}