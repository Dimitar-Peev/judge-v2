package com.example.judge.model.binding;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleAddBindingModel {

    @NotBlank(message = "Please, select a username")
    private String username;

    @NotBlank(message = "Please, select a role")
    private String role;

}
