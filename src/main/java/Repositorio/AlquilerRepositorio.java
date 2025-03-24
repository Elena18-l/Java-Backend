package Repositorio;
import Modelo.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlquilerRepositorio extends JpaRepository<Alquiler, Long> {
}
