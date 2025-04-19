package com.example.judge.model.binding;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentAddBindingModel {

    @Min(value = 2, message = "Score must be between 2 and 6 inclusive!")
    @Max(value = 6, message = "Score must be between 2 and 6 inclusive!")
    private int score;

    @Size(min = 3, message = "Comment text content must be more than 3 characters!")
    private String textContent;

    private String homeworkId;

}
