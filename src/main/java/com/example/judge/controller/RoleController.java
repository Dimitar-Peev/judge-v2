package com.example.judge.controller;

import com.example.judge.model.binding.RoleAddBindingModel;
import com.example.judge.model.entity.User;
import com.example.judge.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/roles")
public class RoleController {

    private final UserService userService;
    private final HttpSession httpSession;

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {

        User user = (User) httpSession.getAttribute("user");
        if (!user.isAdmin()){
            return "redirect:/";
        }

        String userId = session.getAttribute("user_id").toString();
        List<String> allUsernames = this.userService.findAllUsernamesExceptCurrent(userId);
        model.addAttribute("usernames", allUsernames);

        return "role-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("roleAddBindingModel") RoleAddBindingModel roleAddBindingModel,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "role-add";
        }

        this.userService.addRoleToUser(roleAddBindingModel.getUsername(), roleAddBindingModel.getRole());
        return "redirect:/";
    }
}
