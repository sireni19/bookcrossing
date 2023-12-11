package com.prokopovich.bookcrossing.dto;

import com.prokopovich.bookcrossing.customannotations.CustomUniqueEmail;
import com.prokopovich.bookcrossing.customannotations.CustomUniqueUsername;
import com.prokopovich.bookcrossing.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetailsImplDto implements UserDetails {
    public UserDetailsImplDto(String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    private Integer id;
    @NotEmpty(message = "Поле не должно быть пустым")
    @NotBlank(message = "Поле не валидно")
    @CustomUniqueUsername
    private String username;

    @NotEmpty(message = "Email не должен быть пустым")
    @Email(message = "Email не валиден")
    @CustomUniqueEmail
    private String email;

    @NotEmpty(message = "Пароль не должен быть пустым")
    @NotBlank(message = "Пароль не валиден")
    @Size(min = 2,message = "Пароль должен состоять минимум из двух символов")
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImplDto build(User user){
        List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        return new UserDetailsImplDto(
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
        return email;
    }

    public String getEmail() {
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

}

