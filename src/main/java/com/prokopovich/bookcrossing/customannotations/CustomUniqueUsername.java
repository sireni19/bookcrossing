package com.prokopovich.bookcrossing.customannotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomUsernameValidator.class)
public @interface CustomUniqueUsername {
    public String message() default "Пользователь с таким именем уже существует";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
