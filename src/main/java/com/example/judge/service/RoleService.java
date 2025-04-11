package com.example.judge.service;

import com.example.judge.model.entity.Role;
import com.example.judge.model.entity.RoleName;

public interface RoleService {

    void init();

    Role findRole(RoleName name);
}
