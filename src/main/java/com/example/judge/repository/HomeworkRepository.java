package com.example.judge.repository;

import com.example.judge.model.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, String> {

    Optional<Homework> findTop1ByOrderByComments();

}
