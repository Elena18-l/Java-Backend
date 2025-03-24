package Repositorio;
import Modelo.Alquiler;
import Modelo.Cliente;
import Modelo.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AlquilerRepositorio extends JpaRepository<Alquiler, Long> {
    Alquiler save (Alquiler alquiler);
    Optional <Alquiler> findById (Long id);
    List<Alquiler> findAll();
    void deleteById(Long id);
    void delete (Alquiler alquiler);
    long count();
    List<Alquiler> FindByCliente (Cliente cliente);
    List<Alquiler> FindByVehiculo(Vehiculo vehiculo);
    List<Alquiler> FindEntreFechas(LocalDate fecha_inicio, LocalDate fecha_fin);
}
