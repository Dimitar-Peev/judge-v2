package com.example.judge.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HomeViewModel {

    private long totalUsersCount;

    private List<String> students;

    private List<String> activeExercises;

}
