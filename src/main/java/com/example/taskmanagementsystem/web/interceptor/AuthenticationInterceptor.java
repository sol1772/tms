package com.example.taskmanagementsystem.web.interceptor;

import com.example.taskmanagementsystem.domain.User;
import com.example.taskmanagementsystem.service.UserDetailsImpl;
import com.example.taskmanagementsystem.web.config.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        User user;
        UserDetailsImpl userDetails = SecurityUtils.getUserDetails();
        if (userDetails != null) {
            user = userDetails.getUser();
        } else {
            user = (User) req.getSession().getAttribute("user");
        }
        if (!Objects.isNull(user)) {
            resp.sendRedirect(req.getContextPath() + "/user/" + user.getId());
            if (logger.isInfoEnabled()) {
                logger.info("User {} redirected from {} to user page", user, req.getRequestURI());
            }
            return false;
        }
        return HandlerInterceptor.super.preHandle(req, resp, handler);
    }
}
