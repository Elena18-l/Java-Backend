package com.BackSpringBoys.Java_Backend.Services;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Repositorio.ClienteRepositorio;
import com.BackSpringBoys.Java_Backend.Repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final ClienteRepositorio clienteRepo;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepositorio usuarioRepositorio, ClienteRepositorio clienteRepo) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.clienteRepo = clienteRepo;
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        Cliente cliente = usuario.getCliente();
        if (cliente.getId() > 0) {
            cliente = clienteRepo.findById(cliente.getId()).orElseThrow();
            usuario.setCliente(cliente);
        }
        usuarioRepositorio.save(usuario);
    }

    @Override
    public boolean existsByEmail(String email) {
        Optional<Usuario> usuario = usuarioRepositorio.findByEmail(email);
        return usuario.isPresent();
    }

    @Override
    public boolean existsByUsername(String username) {
        Optional<Usuario> usuario = usuarioRepositorio.findByUsername(username);
        return usuario.isPresent();
    }

    public boolean emailEnUsoPorOtro(String email, Long usuarioId) {
        Optional<Usuario> usuario = usuarioRepositorio.findByEmail(email);
        return usuario.isPresent() && !usuario.get().getId().equals(usuarioId);
    }

    public boolean usernameEnUsoPorOtro(String username, Long usuarioId) {
        Optional<Usuario> usuario = usuarioRepositorio.findByUsername(username);
        return usuario.isPresent() && !usuario.get().getId().equals(usuarioId);
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepositorio.findByUsername(username);
    }
}