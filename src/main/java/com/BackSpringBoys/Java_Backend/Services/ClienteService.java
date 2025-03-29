package com.BackSpringBoys.Java_Backend.Services;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class ClienteService {

private ClienteRepositorio clienteRepositorio;

     public ClienteService(ClienteRepositorio clienteRepositorio) {
         this.clienteRepositorio = clienteRepositorio;
     }

     public Iterable<Cliente> obtenerTodosLosClientes() {
         return clienteRepositorio.findAll();
     }

     public Optional<Cliente> obtenerClientePorId(Long id) {
         return clienteRepositorio.findById(id);
     }

     public Cliente guardarCliente(Cliente cliente) {
         return clienteRepositorio.save(cliente);
     }

     public void eliminarCliente(Long id) {
         clienteRepositorio.deleteById(id);
     }
}
