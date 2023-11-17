package com.prokopovich.bookcrossing.dto;

import com.prokopovich.bookcrossing.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImplDto implements UserDetails {

    private Integer id;
    @NotEmpty
    @NotBlank
    private String username;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImplDto build(User user){
        List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        return new UserDetailsImplDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorityList
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}

