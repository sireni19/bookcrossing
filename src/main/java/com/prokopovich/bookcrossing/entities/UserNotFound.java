package com.prokopovich.bookcrossing.entities;

public class UserNotFound extends User{//класс-заглушка, когда в БД не найден пользователь
    private static UserNotFound instance;

    private UserNotFound() {

    }

    public static synchronized UserNotFound getInstance() {
        if (instance == null) {
            instance = new UserNotFound();
        }
        return instance;
    }
}
