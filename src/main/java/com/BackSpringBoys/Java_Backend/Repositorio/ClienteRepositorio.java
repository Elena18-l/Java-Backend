package com.BackSpringBoys.Java_Backend.Repositorio;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepositorio extends CrudRepository<Cliente, Long> {
    boolean existsByDni(String dni); //
}
