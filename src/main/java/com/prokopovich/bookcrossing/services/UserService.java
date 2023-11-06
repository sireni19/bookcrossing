package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.dto.UserDtoToShow;
import com.prokopovich.bookcrossing.entities.Role;
import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.exceptions.AdminException;

public interface UserService {
    UserDtoToShow getUserDtoToShow(String username);
    User findUserByUsername(String username);

    void deleteUserByUsername(String username) throws AdminException;
    UserDtoToShow updateUserRole(String username, Role newRole) throws AdminException;
}
