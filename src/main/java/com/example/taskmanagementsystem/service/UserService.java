package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.domain.User;
import com.example.taskmanagementsystem.repository.UserRepository;
import com.example.taskmanagementsystem.service.validation.ValidationRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(User user) {
        User dbUser = userRepository.save(user);
        if (logger.isInfoEnabled()) {
            logger.info("Создан пользователь {}", dbUser);
        }
        return dbUser;
    }

    @Transactional
    public User editUser(User user) {
        User dbUser = userRepository.save(user);
        if (logger.isInfoEnabled()) {
            logger.info("Изменен пользователь {}", dbUser);
        }
        return dbUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public boolean deleteUser(int id) {
        try {
            userRepository.deleteById(id);
            if (logger.isInfoEnabled()) {
                logger.info("Удален пользователь с id {}", id);
            }
            return true;
        } catch (EmptyResultDataAccessException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Ошибка при удалении пользователя с id {}", id);
            }
            return false;
        }
    }

    public boolean passwordIsValid(String password, User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return StringUtils.isEmpty(user.getPasswordHash()) ||
                bCryptPasswordEncoder.matches(password, user.getPasswordHash());
    }

    @Transactional
    public boolean changePassword(String oldPassword, String newPassword, User user) {
        boolean passwordChanged;
        if (passwordIsValid(oldPassword, user)) {
            String newPasswordHash = user.hashPassword(newPassword);
            user.setPasswordHash(newPasswordHash);
            User dbUser = userRepository.save(user);
            passwordChanged = Objects.equals(dbUser.getPasswordHash(), user.getPasswordHash());
            if (logger.isInfoEnabled()) {
                logger.info("Пароль успешно изменен для пользователя {}", dbUser);
            }
        } else {
            if (logger.isWarnEnabled()) {
                logger.warn("Ошибка изменения пароля для пользователя {} (старый пароль неверный)", user);
            }
            throw new ValidationRuntimeException("Старый пароль неверный!");
        }
        return passwordChanged;
    }
}
