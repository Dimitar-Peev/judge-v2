package com.example.judge.model.view;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserProfileViewModel {

    private String username;
    private String email;
    private String git;
    private Set<String> homeworks = new HashSet<>();

}
