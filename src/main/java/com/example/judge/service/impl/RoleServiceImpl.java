package com.example.judge.service.impl;

import com.example.judge.model.entity.Role;
import com.example.judge.model.entity.RoleName;
import com.example.judge.repository.RoleRepository;
import com.example.judge.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

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
}
