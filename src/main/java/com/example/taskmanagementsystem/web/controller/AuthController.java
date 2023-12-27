package com.example.taskmanagementsystem.web.controller;

import com.example.taskmanagementsystem.domain.User;
import com.example.taskmanagementsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/login"})
    public String viewLogin(@RequestParam(name = "email", required = false) String email,
                            HttpSession session, Model model) {
        if (email == null) {
            String errorMessage = null;
            if (session != null) {
                AuthenticationException e = (AuthenticationException)
                        session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                if (e != null) {
                    errorMessage = e.getMessage();
                }
            }
            if (errorMessage != null) {
                model.addAttribute("error", errorMessage);
            }
            return "login";
        } else {
            User user = userService.getUserByEmail(email);
            return "redirect:/user/" + user.getId();
        }
    }
}
