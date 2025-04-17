package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Services.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    private final ClienteService clienteService;

    public RegisterController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "register";
    }
}
