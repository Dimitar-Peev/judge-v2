package com.example.judge.model.service;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserServiceModel {

    private String id;
    private String username;
    private String password;
    private String email;
    private String git;
    private RoleServiceModel role;
    private Set<String> homeworks = new HashSet<>();

}
