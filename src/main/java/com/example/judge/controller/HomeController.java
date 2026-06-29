package com.example.judge.controller;

import com.example.judge.model.view.HomeViewModel;
import com.example.judge.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class HomeController {

    private final UserService userService;

    @GetMapping(value = {"/", "index"})
    public String index() {

        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {

        HomeViewModel homeViewModel = new HomeViewModel();
        homeViewModel.setTotalUsersCount(this.userService.getUsersCount());
        homeViewModel.setStudents(this.userService.findTopScoredStudentsNames());

        model.addAttribute("homeViewModel", homeViewModel);

        return "home";
    }

}
