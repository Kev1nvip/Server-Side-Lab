package com.stu212306102.helloserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stu212306102.helloserver.common.*;
import com.stu212306102.helloserver.dto.UserDTO;
import com.stu212306102.helloserver.entity.User;
import com.stu212306102.helloserver.entity.UserInfo;
import com.stu212306102.helloserver.mapper.UserMapper;
import com.stu212306102.helloserver.mapper.UserInfoMapper;
import com.stu212306102.helloserver.service.UserService;
import com.stu212306102.helloserver.vo.UserDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import cn.hutool.json.JSONUtil;
import java.util.concurrent.TimeUnit;

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

    private static final String CACHE_KEY_PREFIX = "user:detail:";

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;
    // 查询：缓存优先
    @Override
    public Result<UserDetailVO> getUserDetail(Long userId) {
        String key = CACHE_KEY_PREFIX + userId;

        // 1. 查 Redis
        String json = redisTemplate.opsForValue().get(key);
        if (json != null && !json.isBlank()) {
            try {
                UserDetailVO vo = JSONUtil.toBean(json, UserDetailVO.class);
                return Result.success(vo);
            } catch (Exception e) {
                redisTemplate.delete(key);
            }
        }

        // 2. 查数据库
        UserDetailVO detail = userInfoMapper.getUserDetail(userId);
        if (detail == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 3. 写缓存 10 分钟
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(detail), 10, TimeUnit.MINUTES);
        return Result.success(detail);
    }

    // 更新：先更库，再删缓存
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> updateUserInfo(UserInfo userInfo) {
        if (userInfo == null || userInfo.getUserId() == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // ========== 修复在这里 ==========
        // 1. 先根据 userId 查询出真正的 id（主键）
        UserInfo infoFromDb = userInfoMapper.selectOne(
                Wrappers.lambdaQuery(UserInfo.class)
                        .eq(UserInfo::getUserId, userInfo.getUserId())
        );

        if (infoFromDb == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 2. 把主键 id  set 进去
        userInfo.setId(infoFromDb.getId());

        // 3. 现在才能正常更新
        userInfoMapper.updateById(userInfo);
        // ===============================

        redisTemplate.delete(CACHE_KEY_PREFIX + userInfo.getUserId());
        return Result.success("更新成功");
    }

    // 删除：删库 + 删缓存
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> deleteUser(Long userId) {
        userInfoMapper.deleteById(userId);
        redisTemplate.delete(CACHE_KEY_PREFIX + userId);
        return Result.success("删除成功");
    }
}