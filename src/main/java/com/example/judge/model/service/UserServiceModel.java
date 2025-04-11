package com.example.judge.model.service;

import com.example.judge.model.entity.Role;
import lombok.Data;

@Data
public class UserServiceModel {

    private String id;
    private String username;
    private String password;
    private String email;
    private String git;
    private Role role;

}
