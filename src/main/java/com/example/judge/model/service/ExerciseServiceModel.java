package com.example.judge.model.service;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExerciseServiceModel {

    private String id;
    private String name;
    private LocalDateTime startedOn;
    private LocalDateTime dueDate;

}
