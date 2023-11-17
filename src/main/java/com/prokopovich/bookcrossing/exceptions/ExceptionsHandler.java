package com.prokopovich.bookcrossing.exceptions;

import jakarta.persistence.NoResultException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(DuplicateCityException.class)
    public ModelAndView handleDuplicateCityException(DuplicateCityException exDup) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", exDup.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(DuplicateLocationException.class)
    public ModelAndView handleDuplicateLocationException(DuplicateLocationException exDupLoc) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", exDupLoc.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(NoResultException.class)
    public ModelAndView handleNoResultException(NoResultException noRes) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", noRes.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(AdminException.class)
    public ModelAndView handleNoResultException(AdminException ax) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", ax.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(HostException.class)
    public ModelAndView handleHostException(HostException ax) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", ax.getMessage());
        return modelAndView;
    }
    @ExceptionHandler(DuplicateBookException.class)
    public ModelAndView handleDuplicateBookException(DuplicateBookException dx) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", dx.getMessage());
        return modelAndView;
    }
}
