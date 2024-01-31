package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.dto.UserDetailsImplDto;
import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.servicesimpl.GMailSenderService;
import com.prokopovich.bookcrossing.servicesimpl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AuthController {
    private UserServiceImpl userService;
    private GMailSenderService senderService;


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
        User userApp = userService.saveUser(user);
        model.addAttribute("user_id",userApp.getId());
        String to = user.getUsername();// потому что логирование по username в UserDetailsImplDto
        String activationHtml = "<html xmlns:th=\"http://www.thymeleaf.org\">\n<body>"
                + "<h1>Thanks for registration!</h1>"
                + "<p>Push the button to activate your account:</p>"
                + "<form action=\"http://localhost:8080/bcross/register/activation\" method=\"get\">\n"
                + "    <input type=\"hidden\" name=\"user_id\" th:value=\"${user_id}\" />\n"
                + "    <button type=\"submit\">Activation</button>\n"
                + "</form>\n"
                + "</body></html>";

        senderService.sendActivationHTML(to,"Активация аккаунта",activationHtml);

        return "redirect:/register?success";
    }
//    @GetMapping("/register/activation")
//    //TODO не запускает метод
//    public void activation(@RequestParam("user_id")Integer idToActivation){
//        System.out.println("post");
//        userService.activate(idToActivation);
//        System.out.println("Success");
//    }
}
