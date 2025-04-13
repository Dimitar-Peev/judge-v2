package com.example.judge.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class HomeController {

    private final HttpSession httpSession;


    @GetMapping(value = {"/", "index"})
    public String index(Model model) {

        if (this.httpSession.getAttribute("loggedIn") != null) {
            return "home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {

        return "home";
    }

}
