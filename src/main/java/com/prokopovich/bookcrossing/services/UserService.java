package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.dto.UserDtoToShow;
import com.prokopovich.bookcrossing.entities.Role;
import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.exceptions.AdminException;
import com.prokopovich.bookcrossing.exceptions.HostException;

import java.util.List;

public interface UserService {
    UserDtoToShow getUserDtoToShow(String username);
    User findUserByUsername(String username);

    void deleteUserByUsername(String username) throws AdminException;
    UserDtoToShow updateUserRole(String username, Role newRole) throws AdminException;
    List<UserDtoToShow> findHostsByLocationId(Integer locationId);
    User findUserById(Integer id);
    void deleteHostLocation(Integer hostId);
    void setHostLocation(Integer locId,String username) throws HostException;
}
