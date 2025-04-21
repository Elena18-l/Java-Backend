package com.BackSpringBoys.Java_Backend.Controlador;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/home")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String userHome(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "user/home";
    }
}
