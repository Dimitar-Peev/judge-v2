package com.example.judge.model.binding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginBindingModel {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 2, message = "Username length must be minimum 2 characters!")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 3, message = "Password length must be minimum 3 characters!")
    private String password;

}
