package com.example.judge.controller;

import com.example.judge.model.binding.ExerciseAddBindingModel;
import com.example.judge.model.service.ExerciseServiceModel;
import com.example.judge.service.ExerciseService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ModelMapper modelMapper;
    private final HttpSession httpSession;


    @GetMapping("/add")
    public String add(Model model) {
        if (this.httpSession.getAttribute("loggedIn") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("exerciseAddBindingModel")) {
            model.addAttribute("exerciseAddBindingModel", new ExerciseAddBindingModel());
        }

        return "exercise-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid ExerciseAddBindingModel exerciseAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("exerciseAddBindingModel", exerciseAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.exerciseAddBindingModel", bindingResult);
            return "redirect:add";
        }

        ExerciseServiceModel exerciseServiceModel = this.modelMapper.map(exerciseAddBindingModel, ExerciseServiceModel.class);

        exerciseService.add(exerciseServiceModel);

        return "redirect:/";
    }
}
