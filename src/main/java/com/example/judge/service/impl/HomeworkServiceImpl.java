package com.example.judge.service.impl;

import com.example.judge.model.entity.Homework;
import com.example.judge.model.service.HomeworkServiceModel;
import com.example.judge.repository.HomeworkRepository;
import com.example.judge.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final ModelMapper modelMapper;

    @Override
    public void add(HomeworkServiceModel homeworkServiceModel) {
        this.homeworkRepository.saveAndFlush(this.modelMapper.map(homeworkServiceModel, Homework.class));
    }

}
