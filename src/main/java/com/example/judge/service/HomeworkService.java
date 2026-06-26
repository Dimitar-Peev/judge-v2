package com.example.judge.service;

import com.example.judge.model.service.ExerciseServiceModel;
import com.example.judge.model.service.HomeworkServiceModel;
import jakarta.servlet.http.HttpSession;

public interface HomeworkService {

    void add(HomeworkServiceModel homeworkServiceModel, ExerciseServiceModel exerciseServiceModel, HttpSession httpSession);

    HomeworkServiceModel findHomeworksByScoring();

    HomeworkServiceModel findById(String homeworkId);
}
