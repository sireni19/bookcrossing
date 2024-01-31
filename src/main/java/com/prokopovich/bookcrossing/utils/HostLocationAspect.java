package com.prokopovich.bookcrossing.utils;

import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class HostLocationAspect {
    private UserService userService;
    private boolean isHostLocationCreated;
    private static final Logger LOG = LoggerFactory.getLogger(HostLocationAspect.class);

    public HostLocationAspect(UserService userService) {
        this.userService = userService;
        this.isHostLocationCreated = false;
    }
    @Before("execution(* com.prokopovich.bookcrossing.controllers.HostController.*(..))")
    public void createHostLocation() {
        if (!isHostLocationCreated) {
            LOG.info("получение локации хоста");
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession httpSession = request.getSession();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String hostname = authentication.getName();
            User host = userService.findUserByEmail(hostname);
            Location hostLocation = host.getLocation();
            httpSession.setAttribute("hostLocation", hostLocation);
            isHostLocationCreated = true;
        }
    }
}
