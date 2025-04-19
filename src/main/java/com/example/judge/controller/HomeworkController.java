package com.example.judge.controller;

import com.example.judge.model.binding.HomeworkAddBindingModel;
import com.example.judge.model.entity.User;
import com.example.judge.model.service.ExerciseServiceModel;
import com.example.judge.model.service.HomeworkServiceModel;
import com.example.judge.model.service.UserServiceModel;
import com.example.judge.service.ExerciseService;
import com.example.judge.service.HomeworkService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/homework")
public class HomeworkController {

    private final ExerciseService exerciseService;
    private final ModelMapper modelMapper;
    private final HomeworkService homeworkService;

    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("homeworkAddBindingModel")) {
            model.addAttribute("homeworkAddBindingModel", new HomeworkAddBindingModel());
            model.addAttribute("isLate", false);
        }

        List<String> allExerciseNames = exerciseService.findAllExerciseNames();
        model.addAttribute("allExercises", allExerciseNames);

        return "homework-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("homeworkAddBindingModel") HomeworkAddBindingModel homeworkAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession httpSession) {

        ExerciseServiceModel exerciseServiceModel = exerciseService.findByName(homeworkAddBindingModel.getExercise());
        if (exerciseServiceModel == null) {
            return "redirect:add";
        }

        boolean isLate = exerciseServiceModel.getDueDate().isBefore(LocalDateTime.now());

        if (bindingResult.hasErrors() || isLate) {
            redirectAttributes.addFlashAttribute("homeworkAddBindingModel", homeworkAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.homeworkAddBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isLate", true);
            return "redirect:add";
        }

        HomeworkServiceModel homeworkServiceModel = modelMapper.map(homeworkAddBindingModel, HomeworkServiceModel.class);
        homeworkServiceModel.setAddedOn(LocalDateTime.now());
        homeworkServiceModel.setExercise(exerciseServiceModel);
        User user = (User) httpSession.getAttribute("user");
        homeworkServiceModel.setAuthor(modelMapper.map(user, UserServiceModel.class));

        this.homeworkService.add(homeworkServiceModel);

        return "redirect:/";
    }

}
