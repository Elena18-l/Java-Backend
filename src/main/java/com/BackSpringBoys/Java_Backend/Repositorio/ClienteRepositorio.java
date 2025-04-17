package com.BackSpringBoys.Java_Backend.Repositorio;
import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface ClienteRepositorio extends CrudRepository<Cliente, Long> {
    boolean existsByDni(String dni);
    Optional<Cliente> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
