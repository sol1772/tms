package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.domain.Comment;
import com.example.taskmanagementsystem.domain.Task;
import com.example.taskmanagementsystem.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Override
    @EntityGraph(attributePaths = {"comments"})
    List<Task> findAll();

    @EntityGraph(attributePaths = {"comments"})
    List<Task> findTasksByAuthorId(@Param("id") int id);

    @EntityGraph(attributePaths = {"comments"})
    List<Task> findTasksByPerformerId(@Param("id") int id);

    @Query(value = "SELECT t FROM Comment t WHERE t.task.id = :id")
    List<Comment> findCommentsByTaskId(@Param("id") Integer id);

    @EntityGraph(attributePaths = {"comments"})
    Page<Task> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"comments"})
    Page<Task> findByAuthor(User user, Pageable pageable);

    @EntityGraph(attributePaths = {"comments"})
    Page<Task> findByPerformer(User user, Pageable pageable);

    @EntityGraph(attributePaths = {"comments"})
    Page<Task> findByAuthorAndPerformer(User author, User performer, Pageable pageable);
}
