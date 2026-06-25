package com.example.judge.model.binding;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentAddBindingModel {

    @Min(value = 0, message = "The minimum score can be 0%")
    @Max(value = 100, message = "The maximum score can be 100%")
    private int score;

    @Size(min = 3, message = "Comment text content must be more than 3 characters")
    private String textContent;

    private String homeworkId;

}
