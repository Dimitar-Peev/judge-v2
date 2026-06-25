package com.example.judge.model.binding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class HomeworkAddBindingModel {

    @NotBlank(message = "Please, select an exercise")
    private String exercise;

    @Pattern(regexp = "https://github\\.com/.+/.+", message = "Enter git address following this pattern: https://github.com/username/homework")
    private String gitAddress;

}