package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Services.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final ClienteService clienteService;

    public RegisterController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "login/register";
    }

    @PostMapping("/register")
    public String registerCliente(Cliente cliente, Model model) {
        if (clienteService.existsByEmail(cliente.getEmail())) {
            model.addAttribute("error", "El correo electrónico ya está registrado.");
            return "login/register";
        }
        if (clienteService.existsByDni(cliente.getDni())) {
            model.addAttribute("error", "El DNI ya está registrado.");
            return "login/register";
        }
        clienteService.guardarCliente(cliente);
        return "redirect:/login";
    }



}
