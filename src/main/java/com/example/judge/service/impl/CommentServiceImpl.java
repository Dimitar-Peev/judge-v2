package com.example.judge.service.impl;

import com.example.judge.model.entity.Comment;
import com.example.judge.model.service.CommentServiceModel;
import com.example.judge.model.service.HomeworkServiceModel;
import com.example.judge.model.service.UserServiceModel;
import com.example.judge.repository.CommentRepository;
import com.example.judge.service.CommentService;
import com.example.judge.service.HomeworkService;
import com.example.judge.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final HomeworkService homeworkService;
    private final UserService userService;

    @Override
    public CommentServiceModel add(CommentServiceModel commentServiceModel, HttpSession httpSession) {
        HomeworkServiceModel homeworkServiceModel = this.homeworkService.findById(commentServiceModel.getHomework().getId());
        commentServiceModel.setHomework(homeworkServiceModel);

        String userId = httpSession.getAttribute("user_id").toString();
        UserServiceModel user = this.userService.findById(userId);
        commentServiceModel.setAuthor(user);

        Comment comment = this.modelMapper.map(commentServiceModel, Comment.class);

        Comment saved = this.commentRepository.save(comment);
        log.info("Successfully added comment with score [{}] for homework [{}]", comment.getScore(), homeworkServiceModel.getId());

        return this.modelMapper.map(saved, CommentServiceModel.class);
    }

}
