// UsuarioRepositorio.java
package com.BackSpringBoys.Java_Backend.Repositorio;

import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
