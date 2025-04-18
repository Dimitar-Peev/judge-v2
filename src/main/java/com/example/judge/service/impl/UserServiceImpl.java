package com.example.judge.service.impl;

import com.example.judge.model.entity.Role;
import com.example.judge.model.entity.RoleName;
import com.example.judge.model.entity.User;
import com.example.judge.model.service.UserServiceModel;
import com.example.judge.repository.UserRepository;
import com.example.judge.service.RoleService;
import com.example.judge.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final HttpSession httpSession;

    @Override
    public boolean register(UserServiceModel userServiceModel) {

        Optional<User> existingUser =
                this.userRepository.findByUsernameOrEmail(userServiceModel.getUsername(), userServiceModel.getEmail());

        if (existingUser.isPresent()) {
            log.warn("Failed to create user account. User already exists.");
            return false;
        } else {
            User user = this.modelMapper.map(userServiceModel, User.class);
            user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));

            if (this.userRepository.count() == 0) {
                user.setRole(this.roleService.findRole(RoleName.ADMIN));
            } else {
                user.setRole(this.roleService.findRole(RoleName.USER));
            }

            this.userRepository.save(user);
            log.info("Successfully created new user account for username [%s] and id [%s]".formatted(user.getUsername(), user.getId()));
            return true;
        }
    }

    @Override
    public boolean login(UserServiceModel userServiceModel) {

        Optional<User> optionalUser = this.userRepository.findByUsername(userServiceModel.getUsername());
        if (optionalUser.isEmpty()) {
            log.warn("User not exists.");
            return false;
        }

        boolean passMatch = this.passwordEncoder.matches(userServiceModel.getPassword(), optionalUser.get().getPassword());
        if (!passMatch) {
            log.warn("Password does not match.");
            return false;
        }

        httpSession.setAttribute("user", optionalUser.get());
        httpSession.setAttribute("user_id", optionalUser.get().getId());
        httpSession.setAttribute("loggedIn", true);
        log.info("Successfully logged user account with username [%s]".formatted(userServiceModel.getUsername()));
        return true;
    }

    @Override
    public void logout() {
        httpSession.invalidate();
    }

    @Override
    public List<String> findAllUsernamesExceptCurrent(String userId) {
        return this.userRepository.findAllUsernamesExceptCurrent(userId);
    }

    @Override
    public void addRoleToUser(String username, String role) {

        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            RoleName roleName = RoleName.valueOf(role.toUpperCase());
            Role roleEntity = this.modelMapper.map(this.roleService.findRole(roleName), Role.class);
            user.setRole(roleEntity);
            this.userRepository.saveAndFlush(user);
        }
    }

}
