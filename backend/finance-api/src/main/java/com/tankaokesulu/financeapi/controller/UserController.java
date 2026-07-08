package com.tankaokesulu.financeapi.controller;

import com.tankaokesulu.financeapi.Result;
import com.tankaokesulu.financeapi.dto.ChangePasswordDTO;
import com.tankaokesulu.financeapi.entity.User;
import com.tankaokesulu.financeapi.service.UserService;
import com.tankaokesulu.financeapi.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {

        User loginUser =
                userService.login(
                        user.getUsername(),
                        user.getPassword());

        if (loginUser == null) {

            return Result.error("账号或密码错误");

        }

        // 将Integer类型id转为Long，解决类型不匹配报错
        String token =
                JwtUtil.generateToken(Long.valueOf(loginUser.getId()));

        // 返回数据
        Map<String,Object> data =
                new HashMap<>();

        data.put("token",token);

        data.put("userId",loginUser.getId());

        data.put("username",loginUser.getUsername());

        return Result.success(data);

    }
    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {

        userService.register(user);

        return Result.success("注册成功");
    }
    @GetMapping("/info")
    public Result<User> getUserInfo(
            @RequestParam Long userId
    ) {

        User user =
                userService.getById(userId);

        return Result.success(user);
    }
    @PostMapping("/changePassword")
    public Result<String> changePassword(
            @RequestBody ChangePasswordDTO dto
    ) {

        userService.changePassword(dto);

        return Result.success("修改成功");
    }
    @GetMapping("/tokenTest")
    public Result<String> tokenTest() {

        String token =
                JwtUtil.generateToken(1L);

        return Result.success(token);

    }
    @GetMapping("/parseTest")
    public Result<Object> parseTest(
            @RequestParam String token
    ) {

        Claims claims =
                JwtUtil.parseToken(token);

        return Result.success(
                claims.get("userId")
        );

    }

}