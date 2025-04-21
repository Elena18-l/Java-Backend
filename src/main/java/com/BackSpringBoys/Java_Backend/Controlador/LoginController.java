package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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

    @PostMapping("/user/perfil")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String postPerfil(
            @ModelAttribute("cliente") Cliente clienteModificado,
            Authentication authentication
    ) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.findByUsername(username).orElse(null);

        if (usuario == null) return "redirect:/login";

        Cliente cliente = usuario.getCliente();
        cliente.setNombre(clienteModificado.getNombre());
        cliente.setApellido1(clienteModificado.getApellido1());
        cliente.setApellido2(clienteModificado.getApellido2());
        cliente.setDni(clienteModificado.getDni());
        cliente.setFechaNacimiento(clienteModificado.getFechaNacimiento());

        usuario.setUsername(clienteModificado.getUsuario().getUsername());
        usuario.setEmail(clienteModificado.getUsuario().getEmail());

        usuarioService.guardarUsuario(usuario);

        return "redirect:/user/perfil?success=true";
    }
    @GetMapping("/user/perfil")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getPerfil(@RequestParam(value = "success", required = false) String success,
                            Model model,
                            Authentication authentication) {
        String username = authentication.getName();

        Usuario usuario = usuarioService.findByUsername(username).orElse(null);
        if (usuario == null || usuario.getCliente() == null) {
            return "redirect:/login";
        }

        model.addAttribute("cliente", usuario.getCliente());

        if ("true".equals(success)) {
            model.addAttribute("mensajeExito", "Perfil actualizado correctamente.");
        }

        return "clientes/perfil";
    }

    @GetMapping("/user/perfil/editar")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String editarPerfil(Model model, Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.findByUsername(username).orElse(null);

        if (usuario == null || usuario.getCliente() == null) {
            return "redirect:/login";
        }

        model.addAttribute("cliente", usuario.getCliente());

        return "clientes/editPerfil"; // esta es tu vista
    }
}