 package com.BackSpringBoys.Java_Backend.Repositorio;
 import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
 import org.springframework.data.repository.CrudRepository;

 import java.util.List;
 import java.util.Optional;

 public interface VehiculoRepositorio extends CrudRepository<Vehiculo, Long> {
//     Vehiculo save (Vehiculo vehiculo);
//     Optional<Vehiculo> findById(Long id);
//     List<Vehiculo> findAll();
//     void deleteById (Long id);
//     void delete (Vehiculo vehiculo);
//     long count();
     Optional <Vehiculo> findByMatricula (String matricula);
     Void deleteByMatricula (String matricula);
 }
