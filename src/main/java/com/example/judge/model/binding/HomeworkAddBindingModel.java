package com.example.judge.model.binding;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HomeworkAddBindingModel {

    @Size(min = 2, message = "Exercise name length must be more than 2 characters")
    private String exercise;

    @Pattern(regexp = "https:\\/\\/github\\.com\\/.+/.+", message = "Enter git address following this pattern")
    private String gitAddress;

}