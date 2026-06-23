package com.example.judge.service.impl;

import com.example.judge.exception.ResourceNotFoundException;
import com.example.judge.model.entity.Role;
import com.example.judge.model.entity.RoleName;
import com.example.judge.model.service.RoleServiceModel;
import com.example.judge.repository.RoleRepository;
import com.example.judge.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {

        if (this.roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setName(RoleName.ADMIN);
            roleRepository.save(admin);

            Role user = new Role();
            user.setName(RoleName.USER);
            roleRepository.save(user);
        }
    }

    @Override
    public RoleServiceModel findByName(RoleName roleName) {
        return this.roleRepository
                .findByName(roleName)
                .map(role -> this.modelMapper.map(role, RoleServiceModel.class))
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }

    @Override
    public List<String> findAllRoleNames() {
        return this.roleRepository.findAllRoleNames();
    }
}
