package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Services.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "login/register";
    }

    @PostMapping("/register")
    public String registerCliente(
            @ModelAttribute("cliente") Cliente cliente,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("repassword") String repassword,
            Model model
    ) {
        // Validación de contraseñas
        if (!password.equals(repassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "login/register";
        }

        if (usuarioService.existsByUsername(username)) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "login/register";
        }

        if (usuarioService.existsByEmail(email)) {
            model.addAttribute("error", "El correo electrónico ya está registrado.");
            return "login/register";
        }

        // Crear usuario y establecer relación
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setEmail(email);
        usuario.setRol("USER");
        usuario.setEnabled(true);
        usuario.setCliente(cliente);

        cliente.setUsuario(usuario); // bidireccional

        usuarioService.guardarUsuario(usuario);

        return "redirect:/login";
    }
}
