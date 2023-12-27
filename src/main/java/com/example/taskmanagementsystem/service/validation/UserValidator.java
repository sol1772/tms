package com.example.taskmanagementsystem.service.validation;

import com.example.taskmanagementsystem.domain.User;
import com.example.taskmanagementsystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
@Transactional(readOnly = true)
public class UserValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(UserValidator.class);
    private UserRepository userRepository;

    public UserValidator() {
    }

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            String str = "Пользователь с email '" + user.getEmail() + "' уже существует";
            errors.rejectValue("email", "", str);
            logger.warn(str);
        }
    }
}
