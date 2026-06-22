package com.example.judge.service;

import com.example.judge.model.service.UserServiceModel;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {

    boolean register(UserServiceModel userServiceModel);

    boolean login(UserServiceModel userServiceModel, HttpSession session);

    void logout(HttpSession session);

    List<String> findAllUsernamesExceptCurrent(String userId);

    void addRoleToUser(String username, String role);

}
