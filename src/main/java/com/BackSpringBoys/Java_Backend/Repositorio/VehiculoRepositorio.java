 package com.BackSpringBoys.Java_Backend.Repositorio;
 import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
 import org.springframework.data.repository.CrudRepository;

 import java.util.List;
 import java.util.Optional;

 public interface VehiculoRepositorio extends CrudRepository<Vehiculo, Long> {
     Optional <Vehiculo> findByMatricula (String matricula);
     long deleteByMatricula (String matricula);
     boolean existsByMatricula (String matricula);
 }