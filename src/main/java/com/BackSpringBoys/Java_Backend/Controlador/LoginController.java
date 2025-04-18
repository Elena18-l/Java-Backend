package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Services.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final ClienteService clienteService;

    public LoginController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "login/login";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/clientes";
        } else if (request.isUserInRole("ROLE_USER")) {
            return "redirect:/user/clientes";
        }
        return "redirect:/user/clientes";
    }

    @GetMapping("user/perfil")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getPerfil(Model model, Authentication authentication) {
        String username = authentication.getName();
        Cliente cliente = clienteService.findByUsername(username).orElse(null);

        if(cliente == null) {
            return "redirect:/login";
        }

        model.addAttribute("cliente", cliente);
        return "clientes/perfil";
    }
}
