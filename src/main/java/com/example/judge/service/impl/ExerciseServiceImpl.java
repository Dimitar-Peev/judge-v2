package com.example.judge.service.impl;

import com.example.judge.model.entity.Exercise;
import com.example.judge.model.service.ExerciseServiceModel;
import com.example.judge.repository.ExerciseRepository;
import com.example.judge.service.ExerciseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ModelMapper modelMapper;

    @Override
    public ExerciseServiceModel add(ExerciseServiceModel exerciseServiceModel) {

        Exercise exercise = modelMapper.map(exerciseServiceModel, Exercise.class);
        Exercise saved = this.exerciseRepository.save(exercise);
        log.info("Successfully added exercise.");

        return modelMapper.map(saved, ExerciseServiceModel.class);
    }

}

