package com.example.judge.repository;

import com.example.judge.model.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, String> {

    @Query(value = """
            SELECT h.* 
            FROM homeworks h 
            LEFT JOIN comments c ON h.id = c.homework_id 
            GROUP BY h.id 
            ORDER BY COUNT(c.id) DESC, h.id ASC 
            LIMIT 1
            """, nativeQuery = true)
    Optional<Homework> findTopHomeworkByCommentsCount();

}
