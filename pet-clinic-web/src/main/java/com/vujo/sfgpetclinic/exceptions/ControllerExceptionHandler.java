package com.vujo.sfgpetclinic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView numberFormatExceptionHandler(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        String exceptionMessage = ex.getMessage();
        modelAndView.addObject("exceptionMessage", exceptionMessage);
        modelAndView.setViewName("badRequest");

        return modelAndView;
    }
}
