package com.example.judge.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull
    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private Integer score;

    @Size(min = 3, max = 5000)
    @Column(columnDefinition = "text")
    private String textContent;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private User author;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "homework_id", referencedColumnName = "id", nullable = false)
    private Homework homework;

}
