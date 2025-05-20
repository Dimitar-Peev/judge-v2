package com.example.judge.model.service;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HomeworkServiceModel {

    private String id;
    private LocalDateTime addedOn;
    private String gitAddress;
    private UserServiceModel author;
    private ExerciseServiceModel exercise;

}
