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

        // Validación de mayoría de edad
        if (cliente.getFechaNacimiento() != null) {
            LocalDate fechaNacimiento = cliente.getFechaNacimiento();
            LocalDate fechaActual = LocalDate.now();
            Period edad = Period.between(fechaNacimiento, fechaActual);
            if (edad.getYears() < 18) {
                throw new FechaNacException("El cliente debe ser mayor de 18 años.");
            }
        }

        System.out.println("la fecha está bien");

        // Validar DNI duplicado (solo si ha cambiado)
        Optional<Cliente> existente = clienteRepositorio.findById(cliente.getId());
        if (existente.isPresent()) {
            if (!existente.get().getDni().equals(cliente.getDni()) && existsByDni(cliente.getDni())) {
                throw new DniDuplicadoException("El DNI ya está registrado.");
            }
        } else {
            if (existsByDni(cliente.getDni())) {
                throw new DniDuplicadoException("El DNI ya está registrado.");
            }
        }

        // Validar email y username duplicado solo si es nuevo
        if (cliente.getUsuario() != null && cliente.getUsuario().getId() == null) {
            if (usuarioService.existsByEmail(cliente.getUsuario().getEmail())) {
                throw new EmailDuplicadoException("El email ya está registrado.");
            }

            if (usuarioService.existsByUsername(cliente.getUsuario().getUsername())) {
                throw new EmailDuplicadoException("El username ya está registrado.");
            }
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