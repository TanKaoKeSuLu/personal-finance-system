package com.tankaokesulu.financeapi.mapper;

import com.tankaokesulu.financeapi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User login(
            @Param("username") String username,
            @Param("password") String password
    );

    User selectByUsername(String username);
    User selectById(Long id);
    int insert(User user);

    // 新增修改密码方法，参数名称和XML保持一致
    void updatePassword(@Param("id") Long id, @Param("password") String password);
}