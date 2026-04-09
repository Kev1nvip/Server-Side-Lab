package com.stu212306102.helloserver.service;

import com.stu212306102.helloserver.common.Result;
import com.stu212306102.helloserver.dto.UserDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UserService {
    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
    Result<String> getUserById(Long id);
    // 获取分页用户列表
    Result<Object> getUserPage(Integer pageNum, Integer pageSize);
}