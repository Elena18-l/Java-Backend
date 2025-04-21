package com.BackSpringBoys.Java_Backend.Services;

import com.BackSpringBoys.Java_Backend.Exceptions.DniDuplicadoException;
import com.BackSpringBoys.Java_Backend.Exceptions.EmailDuplicadoException;
import com.BackSpringBoys.Java_Backend.Exceptions.FechaNacException;
import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepositorio clienteRepositorio;
    private final UsuarioService usuarioService;

    public ClienteService(ClienteRepositorio clienteRepositorio, UsuarioService usuarioService) {
        this.clienteRepositorio = clienteRepositorio;
        this.usuarioService = usuarioService;
    }

    public Iterable<Cliente> obtenerTodosLosClientes() {
        return clienteRepositorio.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepositorio.findById(id);
    }

    public Cliente guardarCliente(Cliente cliente) {
        System.out.println("empieza a guardar");

        if (cliente.getFechaNacimiento() != null) {
            LocalDate fechaNacimiento = cliente.getFechaNacimiento();
            LocalDate fechaActual = LocalDate.now();

            Period edad = Period.between(fechaNacimiento, fechaActual);

            if (edad.getYears() < 18) {
                throw new FechaNacException("El cliente debe ser mayor de 18 años.");
            }
        }

        System.out.println("la fecha está bien");

        if (this.existsByDni(cliente.getDni())) {
            System.out.println("el dni ya existe");
            throw new DniDuplicadoException("El DNI ya está registrado.");
        }

        if (usuarioService.existsByEmail(cliente.getUsuario().getEmail())) {
            System.out.println("el email ya existe");
            throw new EmailDuplicadoException("El email ya está registrado.");
        }

        if (usuarioService.existsByUsername(cliente.getUsuario().getUsername())) {
            System.out.println("el username ya existe");
            throw new EmailDuplicadoException("El username ya está registrado.");
        }

        return clienteRepositorio.save(cliente);
    }

    public void eliminarCliente(Long id) {
        clienteRepositorio.deleteById(id);
    }

    public boolean existsByDni(String dni) {
        return clienteRepositorio.existsByDni(dni);
    }
}