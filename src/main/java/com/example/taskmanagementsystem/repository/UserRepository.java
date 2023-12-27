package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    List<User> findAll();

    @EntityGraph(attributePaths = {"tasks"})
    User findUserById(@Param("id") Integer id);

    User findUserByEmail(String email);
}
