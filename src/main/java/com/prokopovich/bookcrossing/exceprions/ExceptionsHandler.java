package com.prokopovich.bookcrossing.exceprions;

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
}
