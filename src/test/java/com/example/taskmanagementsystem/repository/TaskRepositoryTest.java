package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.TestConfig;
import com.example.taskmanagementsystem.domain.Comment;
import com.example.taskmanagementsystem.domain.Task;
import com.example.taskmanagementsystem.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "/truncate_tables.sql", executionPhase = BEFORE_TEST_METHOD)
class TaskRepositoryTest {
    private static final String DELIMITER = "----------------------------------";
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private DataSource dataSource;

    @BeforeAll
    void init() throws SQLException {
        System.out.println(DELIMITER);
        System.out.println("Test TaskRepository.beforeAll");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("create_tables.sql"));
    }

    User getNewUser() {
        return new User("Alina Zagitova", "info@alinazagitova.ru");
    }

    User getNewTargetUser() {
        return new User("Kamila Valieva", "info@valievakamila.ru");
    }

    Task getNewTask(User user) {
        return new Task("Testing project", user);
    }

    @Test
    void save() {
        System.out.println(DELIMITER);
        System.out.println("Test TaskRepository.save()");
        User dbUser = userRepo.save(getNewUser());
        assertNotNull(dbUser);
        Task dbTask = taskRepo.save(getNewTask(dbUser));
        assertNotNull(dbTask);
        System.out.println("Saved " + dbUser);
    }

    @Test
    void findTasksByAuthorId() {
        System.out.println(DELIMITER);
        System.out.println("Test TaskRepository.findTasksByAuthorId()");
        User author = userRepo.save(getNewUser());
        assertNotNull(author);
        Task dbTask = taskRepo.save(getNewTask(author));
        assertNotNull(dbTask);
        List<Task> tasks = taskRepo.findTasksByAuthorId(author.getId());
        assertEquals(1, tasks.size());
        System.out.printf("Found %d task(s) by author %s%n", tasks.size(), author);
    }

    @Test
    void findTasksByPerformerId() {
        System.out.println(DELIMITER);
        System.out.println("Test TaskRepository.findTasksByPerformerId()");
        User author = userRepo.save(getNewUser());
        assertNotNull(author);
        User performer = userRepo.save(getNewTargetUser());
        assertNotNull(performer);
        Task dbTask = taskRepo.save(getNewTask(author));
        assertNotNull(dbTask);
        dbTask.setPerformer(performer);
        taskRepo.save(dbTask);
        List<Task> tasks = taskRepo.findTasksByPerformerId(performer.getId());
        assertEquals(1, tasks.size());
        System.out.printf("Found %d task(s) by performer %s%n", tasks.size(), performer);
    }

    @Test
    void findCommentsByTaskId() {
        System.out.println(DELIMITER);
        System.out.println("Test TaskRepository.findCommentsByTaskId()");
        User author = userRepo.save(getNewUser());
        assertNotNull(author);
        User performer = userRepo.save(getNewTargetUser());
        assertNotNull(performer);
        Task dbTask = taskRepo.save(getNewTask(author));
        assertNotNull(dbTask);
        dbTask.setPerformer(performer);
        List<Comment> comments = dbTask.getComments();
        comments.add(new Comment("Started the task", performer, dbTask));
        taskRepo.save(dbTask);
        comments = taskRepo.findCommentsByTaskId(dbTask.getId());
        assertEquals(1, comments.size());
        System.out.printf("Found %d comment(s) by task %s%n", comments.size(), dbTask);
    }
}