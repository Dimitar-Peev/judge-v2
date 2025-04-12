package com.example.judge.service;

import com.example.judge.model.service.UserServiceModel;

public interface UserService {

    boolean register(UserServiceModel userServiceModel);

    boolean login(UserServiceModel userServiceModel);

    void logout();

}
