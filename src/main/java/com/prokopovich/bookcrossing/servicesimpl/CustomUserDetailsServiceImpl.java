package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.dto.UserDetailsImplDto;
import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService  {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username.trim())
                .orElseThrow(()->new UsernameNotFoundException("User with email +"+username+" not found"));
        return UserDetailsImplDto.build(user);
    }
}
