package com.example.judge.repository;

import com.example.judge.model.entity.Role;
import com.example.judge.model.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(RoleName name);

    @Query(value = "SELECT name FROM roles ORDER BY name", nativeQuery = true)
    List<String> findAllRoleNames();

}
