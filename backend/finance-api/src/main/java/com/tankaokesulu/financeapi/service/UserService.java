package com.tankaokesulu.financeapi.service;

import com.tankaokesulu.financeapi.dto.ChangePasswordDTO;
import com.tankaokesulu.financeapi.entity.User;

public interface UserService {
    User login(String username, String password);
    boolean register(User user);
    User getById(Long id);
    void changePassword(ChangePasswordDTO dto);
}