package com.stu212306102.helloserver.service;

import com.stu212306102.helloserver.common.Result;
import com.stu212306102.helloserver.dto.UserDTO;

public interface UserService {
    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
}