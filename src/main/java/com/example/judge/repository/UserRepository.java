package com.example.judge.repository;

import com.example.judge.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u.username FROM User u WHERE u.id <> :currentUserId")
    List<String> findAllUsernamesExceptCurrent(@Param("currentUserId") String currentUserId);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.homeworks h " +
            "LEFT JOIN FETCH h.exercise " +
            "WHERE u.id = :id")
    Optional<User> findByIdWithHomeworksAndExercises(@Param("id") String id);

}
