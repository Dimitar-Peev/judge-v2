package com.example.judge.service.impl;

import com.example.judge.exception.ResourceNotFoundException;
import com.example.judge.model.entity.Homework;
import com.example.judge.model.service.ExerciseServiceModel;
import com.example.judge.model.service.HomeworkServiceModel;
import com.example.judge.model.service.UserServiceModel;
import com.example.judge.repository.HomeworkRepository;
import com.example.judge.service.HomeworkService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final ModelMapper modelMapper;

    @Override
    public void add(HomeworkServiceModel homeworkServiceModel, ExerciseServiceModel exerciseServiceModel, HttpSession httpSession) {
        homeworkServiceModel.setAddedOn(LocalDateTime.now());
        homeworkServiceModel.setExercise(exerciseServiceModel);
        UserServiceModel user = (UserServiceModel) httpSession.getAttribute("user");
        homeworkServiceModel.setAuthor(user);

        Homework homework = this.modelMapper.map(homeworkServiceModel, Homework.class);

        this.homeworkRepository.save(homework);
    }

    @Override
    public HomeworkServiceModel findHomeworksByScoring() {
        return this.homeworkRepository.findTopHomeworkByCommentsCount()
                .map(homework -> modelMapper.map(homework, HomeworkServiceModel.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not found any homework with comments"));
    }

    @Override
    public HomeworkServiceModel findById(String homeworkId) {
        return this.homeworkRepository.findById(homeworkId)
                .map(homework -> this.modelMapper.map(homework, HomeworkServiceModel.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not found homework with id: " + homeworkId));
    }

}
