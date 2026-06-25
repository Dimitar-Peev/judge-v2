package com.example.judge.repository;

import com.example.judge.model.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String> {

    @Query(value = "SELECT name FROM exercises ORDER BY started_on", nativeQuery = true)
    List<String> findAllExerciseNames();

    Optional<Exercise> findByName(String exercise);

}
