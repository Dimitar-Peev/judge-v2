package com.example.judge.service;

import com.example.judge.model.service.UserServiceModel;

import java.util.List;

public interface UserService {

    boolean register(UserServiceModel userServiceModel);

    boolean login(UserServiceModel userServiceModel);

    void logout();

    List<String> findAllUsernamesExceptCurrent(String userId);

    void addRoleToUser(String username, String role);

}
