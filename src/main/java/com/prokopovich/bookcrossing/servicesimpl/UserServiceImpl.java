package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.dto.UserDetailsImplDto;
import com.prokopovich.bookcrossing.dto.UserDtoToShow;
import com.prokopovich.bookcrossing.entities.Role;
import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.entities.UserNotFound;
import com.prokopovich.bookcrossing.exceptions.AdminException;
import com.prokopovich.bookcrossing.exceptions.HostException;
import com.prokopovich.bookcrossing.repositories.LocationRepository;
import com.prokopovich.bookcrossing.repositories.UserRepository;
import com.prokopovich.bookcrossing.services.UserService;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByUsername(String username) throws NoResultException {
        Optional<User> optional = userRepository.findUserByUsername(username.trim());
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoResultException();
        }
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) throws AdminException {
        User user = findUserByUsername(username);
        if (user.getRole() == Role.ADMIN_ROLE) {
            throw new AdminException("You cannot delete ADMINISTRATOR");
        } else {
            userRepository.deleteUser(username);
        }
    }

    @Override
    public UserDtoToShow updateUserRole(String username, Role newRole) throws AdminException {
        User user = findUserByUsername(username);
        if (user.getRole() == Role.ADMIN_ROLE) {
            throw new AdminException("You cannot change the user role,which has the administrator role");
        } else {
            user.setRole(newRole);
            return convertFromUserToUserDtoShow(userRepository.save(user));
        }
    }

    @Override
    @Transactional
    public void deleteHostLocation(Integer id) {
        userRepository.deleteHostLocation(id);
    }

    @Override
    @Transactional
    public void setHostLocation(Integer locId, String username) throws HostException {
        Optional<User> optionalHost = userRepository.findUserByUsername(username);
        if (optionalHost.isEmpty()) {
            throw new HostException("No such user");
        } else if (optionalHost.isPresent() && optionalHost.get().getRole() != Role.HOST_ROLE) {
            throw new HostException("Such user isn`t HOST");
        } else if (optionalHost.isPresent() && optionalHost.get().getRole() == Role.HOST_ROLE && optionalHost.get().getLocation() != null) {
            throw new HostException("Such user has location already");
        } else {
            userRepository.setHostLocation(locationRepository.findById(locId).get(), username);
        }

    }

    @Override
    public UserDtoToShow getUserDtoToShow(String username) throws NoResultException {
        User user = findUserByUsername(username.trim());
        if (user.getRole() == Role.HOST_ROLE && user.getLocation() != null) {
            System.out.println();
            user.setLocation(locationRepository.findById(user.getLocation().getId()).get());
            return convertFromUserToUserDtoShow(user);
        } else {
            return convertFromUserToUserDtoShow(user);
        }
    }

    @Override
    public List<UserDtoToShow> findHostsByLocationId(Integer locationId) {
        Optional<List<User>> optional = userRepository.findUsersByLocationId(locationId);
        if (optional.isPresent()) {
            List<User> hosts = optional.get();
            List<UserDtoToShow> hostsdto = hosts.stream().filter(user -> user.getRole() == Role.HOST_ROLE)
                    .map(user -> convertFromUserToUserDtoShow(user)).collect(Collectors.toList());
            return hostsdto;
        } else {
            return (List<UserDtoToShow>) UserNotFound.getInstance();
        }
    }

    private UserDtoToShow convertFromUserToUserDtoShow(User user) {
        UserDtoToShow dto = new UserDtoToShow(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getLocation()
        );
        return dto;
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> optional = userRepository.findUserByEmail(email);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return UserNotFound.getInstance();
        }
    }


    @Override
    public User saveUser(UserDetailsImplDto dto) {
        User user = new User();
        user.setUsername(dto.getEmail().trim());
        user.setEmail(dto.getUsername().trim());
        //encrypt the password once we integrate spring security
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        return user;
    }

//    @Override
//    public void activate(Integer id) {
//        userRepository.activationById(id);
//    }
}

