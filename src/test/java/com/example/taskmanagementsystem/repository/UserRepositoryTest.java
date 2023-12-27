package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.TestConfig;
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
class UserRepositoryTest {
    private static final String DELIMITER = "----------------------------------";
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private DataSource dataSource;

    @BeforeAll
    void init() throws SQLException {
        System.out.println(DELIMITER);
        System.out.println("Test UserRepository.beforeAll");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("create_tables.sql"));
    }

    User getNewUser() {
        return new User("Alina Zagitova", "info@alinazagitova.ru");
    }

    User getNewTargetUser() {
        return new User("Kamila Valieva", "info@valievakamila.ru");
    }

    @Test
    void save() {
        System.out.println(DELIMITER);
        System.out.println("Test UserRepository.save()");
        User dbUser = userRepo.save(getNewUser());
        assertNotNull(dbUser);
        System.out.println("Saved " + dbUser);
    }

    @Test
    void findUserById() {
        System.out.println(DELIMITER);
        System.out.println("Test UserRepository.findUserById()");
        User dbUser = userRepo.save(getNewUser());
        assertNotNull(dbUser);
        assertEquals(dbUser, userRepo.findUserById(dbUser.getId()));
        System.out.println("Found by id " + dbUser);
    }

    @Test
    void findUserByEmail() {
        System.out.println(DELIMITER);
        System.out.println("Test UserRepository.findUserByEmail()");
        User dbUser = userRepo.save(getNewUser());
        assertNotNull(dbUser);
        assertEquals(dbUser, userRepo.findUserByEmail(dbUser.getEmail()));
        System.out.println("Found by email " + dbUser);
    }

    @Test
    void findAll() {
        System.out.println(DELIMITER);
        System.out.println("Test UserRepository.findAll()");
        User dbUser = userRepo.save(getNewUser());
        User dbTargetUser = userRepo.save(getNewTargetUser());
        assertNotNull(dbUser);
        assertNotNull(dbTargetUser);
        List<User> users = userRepo.findAll();
        assertEquals(2, users.size());
        System.out.println("Found all clients" + users);
    }
}