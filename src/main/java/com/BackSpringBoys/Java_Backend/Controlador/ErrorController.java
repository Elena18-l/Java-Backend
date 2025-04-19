package com.BackSpringBoys.Java_Backend.Controlador;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(Exception ex, Model model) {
        model.addAttribute("errorMessage","¡Alto ahí! No tienes permiso para entrar en esta zona restringida. 🤖🔒");
        model.addAttribute("errorCode", HttpStatus.FORBIDDEN.value());
        return "error";
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Algo explotó dentro del servidor... ¡pero no es mi culpa! 😅💥");
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error";
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, Model model) {
        model.addAttribute("errorMessage", "Este método no está permitido... pero yo tampoco sé por qué. 🙈🔁");
        model.addAttribute("errorCode", HttpStatus.METHOD_NOT_ALLOWED.value());
        return "error";
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(HttpMessageNotReadableException ex, Model model) {
        model.addAttribute("errorMessage", "Tu petición me ha dejado confundido... ¿puedes reformularla? 🤯📦");
        model.addAttribute("errorCode", HttpStatus.BAD_REQUEST.value());
        return "error";
    }

}
