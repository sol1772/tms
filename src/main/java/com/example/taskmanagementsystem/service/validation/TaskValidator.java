package com.example.taskmanagementsystem.service.validation;

import com.example.taskmanagementsystem.domain.Task;
import com.example.taskmanagementsystem.domain.User;
import com.example.taskmanagementsystem.repository.TaskRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(TaskValidator.class);
    private TaskRepository taskRepository;

    public TaskValidator() {
    }

    @Autowired
    public TaskValidator(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Task.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HttpSession session = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        Task task = (Task) target;
        User user = (User) session.getAttribute("user");
        Optional<Task> optionalTask = taskRepository.findById(task.getId());
        if (optionalTask.isPresent()) {
            Task prevTask = optionalTask.get();
            if (prevTask.getAuthor().getId() != user.getId() && prevTask.getPerformer().getId() != user.getId()) {
                String str = "Пользователь " + user + " не может изменять задачу №" + task.getId();
                errors.rejectValue("priority", "", str);
                logger.warn(str);
            }
            if (prevTask.getAuthor().getId() != user.getId() && prevTask.getPerformer().getId() == user.getId()) {
                String field = "";
                if (!prevTask.getTitle().equals(task.getTitle())) {
                    field = "title";
                } else if (!prevTask.getPriority().equals(task.getPriority())) {
                    field = "priority";
                } else if (!prevTask.getDescription().equals(task.getDescription())) {
                    field = "description";
                } else if (prevTask.getPerformer().getId() != task.getPerformer().getId()) {
                    field = "performer";
                }
                if (!field.isEmpty()) {
                    String str = "Исполнитель " + user + " может изменять только статус задачи №" + task.getId();
                    errors.rejectValue(field, "", str);
                    logger.warn(str);
                }
            }
        }
    }

    public void validateDeletion(Object target, Errors errors) {
        HttpSession session = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        Task task = (Task) target;
        User user = (User) session.getAttribute("user");
        Optional<Task> optionalTask = taskRepository.findById(task.getId());
        if (optionalTask.isPresent()) {
            Task prevTask = optionalTask.get();
            if (prevTask.getAuthor().getId() != user.getId()) {
                String str = "У пользователя " + user + " нет доступа на удаление задачи №" + task.getId();
                errors.rejectValue("priority", "", str);
                logger.warn(str);
            }
        }
    }
}
