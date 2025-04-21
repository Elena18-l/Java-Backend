package com.BackSpringBoys.Java_Backend.Services;

import com.BackSpringBoys.Java_Backend.Exceptions.DniDuplicadoException;
import com.BackSpringBoys.Java_Backend.Exceptions.EmailDuplicadoException;
import com.BackSpringBoys.Java_Backend.Exceptions.FechaNacException;
import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Repositorio.ClienteRepositorio;
import com.BackSpringBoys.Java_Backend.Repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepositorio clienteRepositorio;
    private final UsuarioService usuarioService;
    private final UsuarioRepositorio usuarioRepositorio;

    public ClienteService(ClienteRepositorio clienteRepositorio, UsuarioService usuarioService, UsuarioRepositorio usuarioRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.usuarioService = usuarioService;
        this.usuarioRepositorio = usuarioRepositorio;
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
        Optional<Cliente> clienteOpt = clienteRepositorio.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            Usuario usuario = cliente.getUsuario();

            if (usuario != null) {
                cliente.setUsuario(null);
                clienteRepositorio.save(cliente); // Desvincula primero
                usuarioRepositorio.delete(usuario); // Luego elimina el usuario
            }

            clienteRepositorio.delete(cliente); // Finalmente elimina el cliente
        }
    }

    public boolean existsByDni(String dni) {
        return clienteRepositorio.existsByDni(dni);
    }
}