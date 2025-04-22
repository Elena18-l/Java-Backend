package com.example.alquilervehiculos.controlador;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioControlador {

    @GetMapping("/")
    public String redirigirSegunRol(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/dashboard";
            } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                return "redirect:/user/home";
            }
        }
        return "index"; // si es anónimo, muestra el index
    }
}
