package com.example.judge.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String git;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Homework> homeworks = new HashSet<>();

}
