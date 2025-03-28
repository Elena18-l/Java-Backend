 package com.BackSpringBoys.Java_Backend.Repositorio;
 import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
 import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
 import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
 import org.springframework.data.repository.CrudRepository;

// import java.time.LocalDate;
 import java.util.List;


 public interface AlquilerRepositorio extends CrudRepository<Alquiler, Long> {
//     Alquiler save (Alquiler alquiler);
//     Optional <Alquiler> findById (Long id);
//     List<Alquiler> findAll();
//     void deleteById(Long id);
//     void delete (Alquiler alquiler);
//     long count();
     List<Alquiler> findByCliente (Cliente cliente);
     List<Alquiler> findByVehiculo(Vehiculo vehiculo);
//     List<Alquiler> findEntreFechas(LocalDate fechaInicio, LocalDate fechaFin);
 }
