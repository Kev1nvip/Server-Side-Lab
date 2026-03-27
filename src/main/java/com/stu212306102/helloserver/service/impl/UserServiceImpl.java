package com.stu212306102.helloserver.service.impl;

import com.stu212306102.helloserver.common.Result;
import com.stu212306102.helloserver.dto.UserDTO;
import com.stu212306102.helloserver.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Result<String> register(UserDTO userDTO) {
        return Result.success("注册成功");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        return Result.success("登录成功");
    }
}