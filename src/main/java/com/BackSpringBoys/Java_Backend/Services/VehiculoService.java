package com.BackSpringBoys.Java_Backend.Services;

import com.BackSpringBoys.Java_Backend.Exceptions.MatriculaRegException;
import com.BackSpringBoys.Java_Backend.Exceptions.MatriculaRepetidaException;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Repositorio.VehiculoRepositorio;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiculoService {

    private VehiculoRepositorio vehiculoRepositorio;

    public VehiculoService(VehiculoRepositorio vehiculoRepositorio) {
        this.vehiculoRepositorio = vehiculoRepositorio;
    }

    public Iterable<Vehiculo> obternerTodosLosVehiculos() {
        return vehiculoRepositorio.findAll();
    }

    public Optional<Vehiculo> obtenerVehiculoPorId(Long id) {
        return vehiculoRepositorio.findById(id);
    }

    public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
        if (this.existeVehiculoPorMatricula(vehiculo.getMatricula())) {
            throw new MatriculaRepetidaException("La matrícula ya existe");
        }
        return vehiculoRepositorio.save(vehiculo);
    }

    public void eliminarVehiculo(Long id) {
        vehiculoRepositorio.deleteById(id);
    }

    public Optional<Vehiculo> obtenerVehiculoPorMatricula(String matricula) {
        return vehiculoRepositorio.findByMatricula(matricula);
    }

    public boolean existeVehiculoPorMatricula(String matricula) {
        return vehiculoRepositorio.existsByMatricula(matricula);
    }

    public void eliminarVehiculoPorMatricula(String matricula) {
        vehiculoRepositorio.deleteByMatricula(matricula);
    }

}
