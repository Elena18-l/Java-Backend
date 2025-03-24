package Repositorio;
import Modelo.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepositorio extends JpaRepository<Vehiculo, Long> {
    Vehiculo save (Vehiculo vehiculo);
    Optional<Vehiculo> findById(Long id);
    List<Vehiculo> findAll();
    void deleteById (Long id);
    void delete (Vehiculo vehiculo);
    long count();
    List <Vehiculo> findByMatricula (String matricula);

}
