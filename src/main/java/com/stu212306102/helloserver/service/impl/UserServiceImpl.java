package com.stu212306102.helloserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu212306102.helloserver.common.*;
import com.stu212306102.helloserver.dto.UserDTO;
import com.stu212306102.helloserver.entity.User;
import com.stu212306102.helloserver.mapper.UserMapper;
import com.stu212306102.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service // 必须添加该注解，将业务类交给 Spring 容器管理
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO userDTO) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, userDTO.getUsername());
        User exist = userMapper.selectOne(wrapper);
        if (exist != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // 建议加密
        userMapper.insert(user);
        return Result.success("注册成功!");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, userDTO.getUsername());
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        if (!userDTO.getPassword().equals(user.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }
        // 生成 Token 并返回
        String token = "Bearer " + JwtUtil.generateToken(userDTO.getUsername());
        return Result.success(token);
    }

    @Override
    public Result<String> getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(user.toString());
    }
}