package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.dto.UserDtoToShow;
import com.prokopovich.bookcrossing.entities.Role;
import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.exceptions.AdminException;
import com.prokopovich.bookcrossing.repositories.UserRepository;
import com.prokopovich.bookcrossing.services.UserService;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUsername(String username) throws NoResultException {
        Optional<User> optional = userRepository.findUserByUsername(username.trim());
        if(optional.isPresent()){
            return  optional.get();
        }else {
            throw new NoResultException();
        }
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) throws AdminException {
        User user = findUserByUsername(username);
        if(user.getRole()== Role.ADMIN_ROLE){
            throw new AdminException("You cannot delete ADMINISTRATOR");
        }else {
            userRepository.deleteUser(username);
        }
    }

    @Override
    public UserDtoToShow updateUserRole(String username,Role newRole) throws AdminException {
        User user =findUserByUsername(username);
        if(user.getRole()==Role.ADMIN_ROLE){
            throw new AdminException("You cannot change the user role,which has the administrator role");
        }else {
            user.setRole(newRole);
            return convertFromUserToUserDtoShow(userRepository.save(user));
        }
    }

    @Override
    public UserDtoToShow getUserDtoToShow(String username) throws NoResultException{
        return convertFromUserToUserDtoShow(findUserByUsername(username));
    }

    private UserDtoToShow convertFromUserToUserDtoShow(User user) {
        UserDtoToShow dto = new UserDtoToShow();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.isActivate());
        return dto;
    }
}
