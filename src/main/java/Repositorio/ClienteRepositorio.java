package Repositorio;
import Modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
    void deleteById(Long id);
    void delete(Cliente cliente);
    long count();
    List<Cliente> findByDni(String dni);
    List<Cliente> findByNombre(String nombre);
    List<Cliente> findByApellido1AndApellido2(String apellido1, String apellido2);
    List<Cliente> findByNombreAndApellido1(String nombre, String apellido1);










}
