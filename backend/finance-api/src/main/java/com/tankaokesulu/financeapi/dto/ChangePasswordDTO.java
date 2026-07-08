package com.tankaokesulu.financeapi.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private Long userId;

    // 旧密码
    private String oldPassword;

    // 新密码
    private String newPassword;
}