package com.example.judge.model.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterBindingModel {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 2, message = "Username length must be minimum 2 characters!")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 3, message = "Password length must be minimum 3 characters!")
    private String password;

    private String confirmPassword;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Enter valid email address")
    private String email;

    @NotBlank(message = "GitHub profile cannot be empty")
    @Pattern(regexp = "https://github\\.com/.+", message = "Enter valid github address")
    private String git;

}
