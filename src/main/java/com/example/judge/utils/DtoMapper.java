package com.example.judge.utils;

import com.example.judge.model.binding.CommentAddBindingModel;
import com.example.judge.model.service.CommentServiceModel;
import com.example.judge.model.service.HomeworkServiceModel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {

    public static CommentServiceModel mapCommentAddBindingModelToCommentServiceModel(CommentAddBindingModel commentAddBindingModel) {
        if (commentAddBindingModel == null) {
            return null;
        }

        CommentServiceModel commentServiceModel = new CommentServiceModel();
        commentServiceModel.setScore(commentAddBindingModel.getScore());
        commentServiceModel.setTextContent(commentAddBindingModel.getTextContent());

        HomeworkServiceModel homeworkServiceModel = new HomeworkServiceModel();
        homeworkServiceModel.setId(commentAddBindingModel.getHomeworkId());

        commentServiceModel.setHomework(homeworkServiceModel);

        return commentServiceModel;
    }
}
