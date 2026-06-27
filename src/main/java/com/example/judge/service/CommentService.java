package com.example.judge.service;

import com.example.judge.model.service.CommentServiceModel;
import jakarta.servlet.http.HttpSession;

public interface CommentService {

    CommentServiceModel add(CommentServiceModel commentServiceModel, HttpSession httpSession);

}
