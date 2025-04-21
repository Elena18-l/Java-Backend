package com.BackSpringBoys.Java_Backend.Services;

import com.BackSpringBoys.Java_Backend.Modelo.Usuario;

public interface UsuarioService {
    void guardarUsuario(Usuario usuario);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
