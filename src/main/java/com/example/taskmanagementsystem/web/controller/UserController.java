package com.example.taskmanagementsystem.web.controller;

import com.example.taskmanagementsystem.domain.User;
import com.example.taskmanagementsystem.domain.dto.UserDto;
import com.example.taskmanagementsystem.service.UserService;
import com.example.taskmanagementsystem.service.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String ERROR = "error";
    private static final String EXC_MSG = "exceptionMessage";
    private static final String USER_ADD = "user-add";
    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("")
    public String viewUsers(Model model) {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            users = new ArrayList<>();
        }
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Integer id, Model model) {
        User user = userService.getUserById(id);
        if (!Objects.isNull(user)) {
            model.addAttribute("user", user);
            return "user";
        } else {
            model.addAttribute(EXC_MSG, "User with id " + id + " not found");
            return ERROR;
        }
    }

    @GetMapping("/add")
    public String viewUserAdd() {
        return USER_ADD;
    }

    @PostMapping("/add")
    public String addAccount(@ModelAttribute UserDto userDto, BindingResult result,
                             @RequestParam Map<String, String> p, Model model) {
        String option = p.get("submit");
        if ("Cancel".equals(option)) {
            return "redirect:/";
        }
        if (!validateFields(userDto)) {
            model.addAttribute(ERROR, "Не заполнены обязательные поля");
            return USER_ADD;
        }
        User user = userDto.toNewUser(userDto);
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            model.addAllAttributes(p);
            model.addAttribute(ERROR, result.getAllErrors().get(0).getDefaultMessage());
            return USER_ADD;
        }
        if ("Register".equals(option)) {
            try {
                User dbUser = userService.createUser(user);
                model.addAttribute("user", dbUser);
                logger.info("Создан пользователь {}", dbUser);
                return "redirect:/user/" + dbUser.getId();
            } catch (Exception e) {
                model.addAttribute(ERROR, e.getMessage());
                return USER_ADD;
            }
        }
        return "redirect:/";
    }

    public boolean validateFields(UserDto dto) {
        return !dto.getName().isEmpty() && !dto.getEmail().isEmpty();
    }
}