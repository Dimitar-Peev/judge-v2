package com.example.judge.service.impl;

import com.example.judge.exception.ResourceNotFoundException;
import com.example.judge.model.entity.Role;
import com.example.judge.model.entity.RoleName;
import com.example.judge.model.entity.User;
import com.example.judge.model.service.RoleServiceModel;
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

    @Override
    public boolean register(UserServiceModel userServiceModel) {

        Optional<User> existingUser = getByUsernameOrEmail(userServiceModel);

        if (existingUser.isPresent()) {
            log.warn("Failed to create user account. User already exists.");
            return false;
        } else {
            User user = this.modelMapper.map(userServiceModel, User.class);
            user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));

            if (this.userRepository.count() == 0) {
                RoleServiceModel adminRoleModel = this.roleService.findByName(RoleName.ADMIN);
                user.setRole(this.modelMapper.map(adminRoleModel, Role.class));
            } else {
                RoleServiceModel userRoleModel = this.roleService.findByName(RoleName.USER);
                user.setRole(this.modelMapper.map(userRoleModel, Role.class));
            }

            User savedUser = this.userRepository.save(user);
            log.info("Successfully created new user account for username [{}] and id [{}]", savedUser.getUsername(), savedUser.getId());
            return true;
        }
    }

    @Override
    public boolean login(UserServiceModel userServiceModel, HttpSession httpSession) {

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

        UserServiceModel existingUser = this.modelMapper.map(optionalUser.get(), UserServiceModel.class);
        existingUser.setPassword(null);

        httpSession.setAttribute("user_id", existingUser.getId());
        httpSession.setAttribute("role", existingUser.getRole().getName());
        httpSession.setAttribute("username", existingUser.getUsername());

        log.info("Successfully logged user account with username [{}]", existingUser.getUsername());
        return true;
    }

    @Override
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }

    @Override
    public List<String> findAllUsernamesExceptCurrent(String userId) {
        return this.userRepository.findAllUsernamesExceptCurrent(userId);
    }

    @Override
    public void addRoleToUser(String username, String role) {

        User user = getByUsername(username);
        RoleName roleName = RoleName.valueOf(role.toUpperCase());

        if (user.getRole() != null && user.getRole().getName() == roleName) {
            log.info("User [{}] already has role [{}]. No changes made.", username, roleName);
            return;
        }

        RoleServiceModel roleServiceByName = this.roleService.findByName(roleName);
        Role roleEntity = this.modelMapper.map(roleServiceByName, Role.class);
        user.setRole(roleEntity);
        this.userRepository.save(user);

        log.info("Successfully changed user role for user [{}] to [{}]", user.getUsername(), user.getRole().getName());
    }

    private User getByUsername(String name) {
        return this.userRepository.findByUsername(name)
                .orElseThrow(() -> new ResourceNotFoundException("User with username [%s] was not found.".formatted(name)));
    }

    private Optional<User> getByUsernameOrEmail(UserServiceModel userServiceModel) {
        return this.userRepository.findByUsernameOrEmail(userServiceModel.getUsername(), userServiceModel.getEmail());
    }

}
