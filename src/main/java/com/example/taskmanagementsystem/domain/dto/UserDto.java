package com.example.taskmanagementsystem.domain.dto;

import com.example.taskmanagementsystem.domain.Role;
import com.example.taskmanagementsystem.domain.Task;
import com.example.taskmanagementsystem.domain.User;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private final List<Task> tasks = new ArrayList<>();
    private int id;
    private String name;
    private String email;
    private String password;

    public UserDto() {
    }

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserDto(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public User toNewUser(UserDto dto) {
        User user = new User(dto.getName(), dto.getEmail());
        if (!StringUtils.isEmpty(dto.getPassword())) {
            user.setPasswordHash(user.hashPassword(dto.getPassword()));
        }

        user.setRole(Role.USER);
        return user;
    }

    public User toUser(User user, UserDto dto) {
        user.setUsername(dto.getName());
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null) {
            user.setPasswordHash(user.hashPassword(dto.getPassword()));
        }
        return user;
    }
}
