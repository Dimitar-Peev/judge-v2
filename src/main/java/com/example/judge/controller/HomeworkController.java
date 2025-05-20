package com.example.judge.controller;

import com.example.judge.model.binding.CommentAddBindingModel;
import com.example.judge.model.binding.HomeworkAddBindingModel;
import com.example.judge.model.entity.User;
import com.example.judge.model.service.CommentServiceModel;
import com.example.judge.model.service.ExerciseServiceModel;
import com.example.judge.model.service.HomeworkServiceModel;
import com.example.judge.model.service.UserServiceModel;
import com.example.judge.model.view.HomeworkViewModel;
import com.example.judge.service.CommentService;
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
    private final CommentService commentService;

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

    @GetMapping("/check")
    public String check(Model model) {

        if (!model.containsAttribute("commentAddBindingModel")) {
            model.addAttribute("commentAddBindingModel", new CommentAddBindingModel());
        }

        HomeworkServiceModel homeworksByScoring = this.homeworkService.findHomeworksByScoring();

        HomeworkViewModel homework = modelMapper.map(homeworksByScoring, HomeworkViewModel.class);

        model.addAttribute("homework", homework);

        return "homework-check";
    }

    @PostMapping("/check")
    public String checkConfirm(@Valid @ModelAttribute("commentAddBindingModel") CommentAddBindingModel commentAddBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentAddBindingModel", commentAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentAddBindingModel", bindingResult);
            return "redirect:check";
        }

        CommentServiceModel commentServiceModel = this.modelMapper.map(commentAddBindingModel, CommentServiceModel.class);
        commentServiceModel.setHomework(this.homeworkService.findById(commentAddBindingModel.getHomeworkId()));
        User user = (User) httpSession.getAttribute("user");
        commentServiceModel.setAuthor(modelMapper.map(user, UserServiceModel.class));

        this.commentService.add(commentServiceModel);

        return "redirect:/";
    }

}
