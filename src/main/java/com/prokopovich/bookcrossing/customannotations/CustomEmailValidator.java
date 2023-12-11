package com.prokopovich.bookcrossing.customannotations;

import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomEmailValidator implements ConstraintValidator<CustomUniqueEmail, String> {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void initialize(CustomUniqueEmail checkedEmail) {
    }

    @Override
    public boolean isValid(String enteredValue, ConstraintValidatorContext context) {
        Optional<User> userFromDatabase = userRepository.findUserByEmail(enteredValue);
        return userFromDatabase.isEmpty();
    }
}