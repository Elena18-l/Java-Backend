package com.BackSpringBoys.Java_Backend.Services;

import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Repositorio.AlquilerRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlquilerService {
    private AlquilerRepositorio alquilerRepositorio;

    public AlquilerService(AlquilerRepositorio alquilerRepositorio) {
        this.alquilerRepositorio = alquilerRepositorio;
    }

    public Iterable<Alquiler> obtenerTodosLosAlquileres() {
        return alquilerRepositorio.findAll();
    }

    public Alquiler guardarAlquiler(Alquiler alquiler) {
        return alquilerRepositorio.save(alquiler);
    }

    public Optional<Alquiler> obtenerAlquilerPorId(Long id) {
        return alquilerRepositorio.findById(id);
    }

    public void eliminarAlquiler(Long id) {
        alquilerRepositorio.deleteById(id);
    }

    public List<Alquiler> findByCliente(Cliente cliente) {
        return alquilerRepositorio.findByCliente(cliente);
    }

    public List<Alquiler> findByVehiculo(Vehiculo vehiculo){
        return alquilerRepositorio.findByVehiculo(vehiculo);
    }
}
