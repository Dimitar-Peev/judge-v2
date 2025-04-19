package com.example.judge.model.service;

import lombok.Data;

@Data
public class CommentServiceModel {

    private int score;
    private String textContent;
    private UserServiceModel author;
    private HomeworkServiceModel homework;

}
