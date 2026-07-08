package com.tankaokesulu.financeapi.service.impl;

import com.tankaokesulu.financeapi.dto.ChangePasswordDTO;
import com.tankaokesulu.financeapi.entity.User;
import com.tankaokesulu.financeapi.exception.BusinessException;
import com.tankaokesulu.financeapi.mapper.UserMapper;
import com.tankaokesulu.financeapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    @Override
    public User login(String username, String password) {

        User user =
                userMapper.selectByUsername(username);

        if (user == null) {
            return null;
        }

        // BCrypt 验证密码
        if (encoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }
    @Override
    public boolean register(User user) {

        User existUser =
                userMapper.selectByUsername(
                        user.getUsername()
                );

        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }

// 密码加密
        user.setPassword(
                encoder.encode(user.getPassword())
        );

        userMapper.insert(user);

        return true;
    }
    @Override
    public User getById(Long id) {

        return userMapper.selectById(id);
    }
    @Override
    public void changePassword(ChangePasswordDTO dto) {

        User user =
                userMapper.selectById(dto.getUserId());

        if(user == null){

            throw new BusinessException("用户不存在");

        }

        if (!encoder.matches(dto.getOldPassword(),
                user.getPassword())) {

            throw new BusinessException("旧密码错误");
        }

        userMapper.updatePassword(
                dto.getUserId(),
                encoder.encode(dto.getNewPassword())
        );
    }
}