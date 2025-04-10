package com.example.judge.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "homeworks")
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private LocalDateTime addedOn;

    @Column(nullable = false, unique = true)
    private String gitAddress;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @OneToMany(mappedBy = "homework", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

}
