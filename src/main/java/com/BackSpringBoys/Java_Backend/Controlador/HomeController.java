package com.BackSpringBoys.Java_Backend.Controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String goHome(Model model) {
        System.out.println("página principal...");
        return "index";
    }
}
