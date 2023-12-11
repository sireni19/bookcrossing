package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.dto.UserDetailsImplDto;
import com.prokopovich.bookcrossing.servicesimpl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthController {
    //TODO check LOGIN logic
    private UserServiceImpl userService;
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // Очистка аутентификации
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        // Редирект на страницу входа после успешного выхода
        return "redirect:/login?logout";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDetailsImplDto user = new UserDetailsImplDto();
        model.addAttribute("user", user);
        return "register";
    }
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDetailsImplDto user,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "register";
        }
        userService.saveUser(user);
        //TODO
//       emailService.sendSimpleMessage(user.getEmail(),"TEST 123", "TEST123");
        return "redirect:/register?success";
    }
}
