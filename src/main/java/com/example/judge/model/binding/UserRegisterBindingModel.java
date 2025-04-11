package com.example.judge.model.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterBindingModel {

    @Size(min = 2, message = "Username length must be minimum 2 characters!")
    private String username;

    @Size(min = 3, message = "Password length must be minimum 3 characters!")
    private String password;

    private String confirmPassword;

    @NotBlank(message = "Enter valid email address")
    @Email(message = "Enter valid email address")
    private String email;

    @Pattern(regexp = "https:\\/\\/github\\.com\\/.+", message = "Enter valid github address")
    private String git;

}
