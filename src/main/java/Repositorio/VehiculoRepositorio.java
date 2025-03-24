package Repositorio;
import Modelo.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VehiculoRepositorio extends JpaRepository<Vehiculo, Long> {
}
