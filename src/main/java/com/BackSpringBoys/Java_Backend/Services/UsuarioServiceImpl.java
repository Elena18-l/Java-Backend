package com.BackSpringBoys.Java_Backend.Services;

import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioServiceImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepositorio.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return usuarioRepositorio.existsByUsername(username);
    }
}
