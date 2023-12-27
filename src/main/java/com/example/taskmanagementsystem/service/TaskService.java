package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.domain.Task;
import com.example.taskmanagementsystem.domain.User;
import com.example.taskmanagementsystem.domain.dto.PageRequestDto;
import com.example.taskmanagementsystem.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(int id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task createTask(Task task) {
        Task dbTask = taskRepository.save(task);
        if (logger.isInfoEnabled()) {
            logger.info("Создана задача {}", dbTask);
        }
        return dbTask;
    }

    @Transactional
    public Task editTask(Task task) {
        Task dbTask = taskRepository.save(task);
        if (logger.isInfoEnabled()) {
            logger.info("Изменена задача {}", dbTask);
        }
        return dbTask;
    }

    @Transactional
    public boolean deleteTask(int id) {
        try {
            taskRepository.deleteById(id);
            if (logger.isInfoEnabled()) {
                logger.info("Удалена задача с id {}", id);
            }
            return true;
        } catch (EmptyResultDataAccessException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Ошибка при удалении задачи с id {}", id);
            }
            return false;
        }
    }

    public Page<Task> getPageAllTasks(PageRequestDto dto) {
        Pageable pageable = new PageRequestDto().getPageable(dto);
        return taskRepository.findAll(pageable);
    }

    public Page<Task> getPageTasksByAuthor(PageRequestDto dto, User author) {
        Pageable pageable = new PageRequestDto().getPageable(dto);
        return taskRepository.findByAuthor(author, pageable);
    }

    public Page<Task> getPageTasksByPerformer(PageRequestDto dto, User performer) {
        Pageable pageable = new PageRequestDto().getPageable(dto);
        return taskRepository.findByPerformer(performer, pageable);
    }

    public Page<Task> getPageTasksByAuthorAndPerformer(PageRequestDto dto, User author, User performer) {
        Pageable pageable = new PageRequestDto().getPageable(dto);
        return taskRepository.findByAuthorAndPerformer(author, performer, pageable);
    }
}
