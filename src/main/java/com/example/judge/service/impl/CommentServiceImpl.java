package com.example.judge.service.impl;

import com.example.judge.model.entity.Comment;
import com.example.judge.model.service.CommentServiceModel;
import com.example.judge.repository.CommentRepository;
import com.example.judge.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    public void add(CommentServiceModel commentServiceModel) {
        Comment comment = this.modelMapper.map(commentServiceModel, Comment.class);
        comment.setId(null);
        this.commentRepository.save(comment);
    }

}
