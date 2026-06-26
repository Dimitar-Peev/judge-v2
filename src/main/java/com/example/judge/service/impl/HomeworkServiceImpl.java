package com.example.judge.service.impl;

import com.example.judge.exception.ResourceNotFoundException;
import com.example.judge.model.entity.Homework;
import com.example.judge.model.service.ExerciseServiceModel;
import com.example.judge.model.service.HomeworkServiceModel;
import com.example.judge.model.service.UserServiceModel;
import com.example.judge.repository.HomeworkRepository;
import com.example.judge.service.HomeworkService;
import com.example.judge.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public void add(HomeworkServiceModel homeworkServiceModel, ExerciseServiceModel exerciseServiceModel, HttpSession httpSession) {
        homeworkServiceModel.setAddedOn(LocalDateTime.now());
        homeworkServiceModel.setExercise(exerciseServiceModel);
        String userId = httpSession.getAttribute("user_id").toString();
        UserServiceModel user = this.userService.findById(userId);
        homeworkServiceModel.setAuthor(user);

        Homework homework = this.modelMapper.map(homeworkServiceModel, Homework.class);

        this.homeworkRepository.save(homework);
        log.info("Successfully added homework.");
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
