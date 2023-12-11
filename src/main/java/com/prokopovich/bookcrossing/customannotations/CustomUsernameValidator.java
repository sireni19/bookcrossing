package com.prokopovich.bookcrossing.customannotations;

import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUsernameValidator implements ConstraintValidator<CustomUniqueUsername, String> {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void initialize(CustomUniqueUsername checkedUsername) {
    }

    @Override
    public boolean isValid(String enteredValue, ConstraintValidatorContext context) {
        Optional<User> userFromDatabase = userRepository.findUserByUsername(enteredValue);
        return userFromDatabase.isEmpty();
    }
}