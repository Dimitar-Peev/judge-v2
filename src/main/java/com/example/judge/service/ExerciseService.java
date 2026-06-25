package com.example.judge.service;

import com.example.judge.model.service.ExerciseServiceModel;

import java.util.List;

public interface ExerciseService {

    ExerciseServiceModel add(ExerciseServiceModel exerciseServiceModel);

    List<String> findAllExerciseNames();

    ExerciseServiceModel findByName(String exercise);
}
