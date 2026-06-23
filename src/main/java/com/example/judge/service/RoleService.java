package com.example.judge.service;

import com.example.judge.model.entity.RoleName;
import com.example.judge.model.service.RoleServiceModel;

import java.util.List;

public interface RoleService {

    void init();

    RoleServiceModel findByName(RoleName roleName);

    List<String> findAllRoleNames();

}
