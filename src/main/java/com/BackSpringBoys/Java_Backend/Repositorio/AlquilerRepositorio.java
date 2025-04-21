package com.BackSpringBoys.Java_Backend.Repositorio;

import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlquilerRepositorio extends CrudRepository<Alquiler, Long> {
    List<Alquiler> findByCliente(Cliente cliente);
    List<Alquiler> findByVehiculo(Vehiculo vehiculo);
}
