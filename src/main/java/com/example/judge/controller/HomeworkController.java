package com.example.judge.controller;

import com.example.judge.model.binding.CommentAddBindingModel;
import com.example.judge.model.binding.HomeworkAddBindingModel;
import com.example.judge.model.service.CommentServiceModel;
import com.example.judge.model.service.ExerciseServiceModel;
import com.example.judge.model.service.HomeworkServiceModel;
import com.example.judge.model.view.HomeworkViewModel;
import com.example.judge.service.CommentService;
import com.example.judge.service.ExerciseService;
import com.example.judge.service.HomeworkService;
import com.example.judge.utils.DtoMapper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.judge.common.Constants.BINDING_MODEL;

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
        }

        if (!model.containsAttribute("isLate")) {
            model.addAttribute("isLate", false);
        }

        List<String> allExerciseNames = this.exerciseService.findAllExerciseNames();
        model.addAttribute("allExercises", allExerciseNames);

        return "homework-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid HomeworkAddBindingModel homeworkAddBindingModel, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("homeworkAddBindingModel", homeworkAddBindingModel);
            redirectAttributes.addFlashAttribute(BINDING_MODEL + "homeworkAddBindingModel", bindingResult);
            return "redirect:/homework/add";
        }

        ExerciseServiceModel exerciseServiceModel = this.exerciseService.findByName(homeworkAddBindingModel.getExercise());
        if (exerciseServiceModel == null) {
            return "redirect:/homework/add";
        }

        boolean isLate = exerciseServiceModel.getDueDate().isBefore(LocalDateTime.now());

        if (isLate) {
            redirectAttributes.addFlashAttribute("isLate", true);
            return "redirect:/homework/add";
        }

        HomeworkServiceModel homeworkServiceModel = this.modelMapper.map(homeworkAddBindingModel, HomeworkServiceModel.class);

        this.homeworkService.add(homeworkServiceModel, exerciseServiceModel, httpSession);

        return "redirect:/";
    }

    @GetMapping("/check")
    public String check(Model model) {

        if (!model.containsAttribute("commentAddBindingModel")) {
            model.addAttribute("commentAddBindingModel", new CommentAddBindingModel());
        }

        HomeworkServiceModel homeworksByScoring = this.homeworkService.findHomeworksByScoring();

        HomeworkViewModel homework = this.modelMapper.map(homeworksByScoring, HomeworkViewModel.class);

        model.addAttribute("homework", homework);

        return "homework-check";
    }

    @PostMapping("/check")
    public String checkConfirm(@Valid CommentAddBindingModel commentAddBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentAddBindingModel", commentAddBindingModel);
            redirectAttributes.addFlashAttribute(BINDING_MODEL + "commentAddBindingModel", bindingResult);
            return "redirect:/homework/check";
        }

        CommentServiceModel commentServiceModel = DtoMapper.mapCommentAddBindingModelToCommentServiceModel(commentAddBindingModel);

        CommentServiceModel added = this.commentService.add(commentServiceModel, httpSession);

        return "redirect:/";
    }
}
