package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Repositorio.UsuarioRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
public class UserRestController {

    private final UsuarioRepositorio usuarioRepositorio;

    public UserRestController(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> getUsuarioActual(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        Usuario usuario = usuarioRepositorio.findByUsername(userDetails.getUsername())
                .orElse(null);

        return ResponseEntity.ok(usuario);
    }
}