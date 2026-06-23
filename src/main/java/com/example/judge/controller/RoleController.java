package com.example.judge.controller;

import com.example.judge.model.binding.RoleAddBindingModel;
import com.example.judge.service.RoleService;
import com.example.judge.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.example.judge.common.Constants.BINDING_MODEL;

@RequiredArgsConstructor
@Controller
@RequestMapping("/roles")
public class RoleController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/add")
    public String add(Model model, HttpSession httpSession) {

        String role = httpSession.getAttribute("role").toString();
        if (!"ADMIN".equals(role)) {
            return "redirect:/";
        }

        if (!model.containsAttribute("roleAddBindingModel")) {
            model.addAttribute("roleAddBindingModel", new RoleAddBindingModel());
        }

        String userId = httpSession.getAttribute("user_id").toString();
        List<String> allUsernames = this.userService.findAllUsernamesExceptCurrent(userId);
        model.addAttribute("usernames", allUsernames);

        List<String> roles = this.roleService.findAllRoleNames();
        model.addAttribute("allRoles", roles);

        return "role-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid RoleAddBindingModel roleAddBindingModel, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("roleAddBindingModel", roleAddBindingModel);
            redirectAttributes.addFlashAttribute(BINDING_MODEL + "roleAddBindingModel", bindingResult);
            return "redirect:/roles/add";
        }

        this.userService.addRoleToUser(roleAddBindingModel.getUsername(), roleAddBindingModel.getRole());

        return "redirect:/";
    }
}
