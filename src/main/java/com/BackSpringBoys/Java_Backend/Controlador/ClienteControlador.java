package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.Optional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

    public ClienteService clienteService;

    public ClienteControlador(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
        return "clientes/listaClientes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioAgregarCliente(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", new Cliente());
        return "clientes/agregarCliente";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "clientes/agregarCliente";
        }

        clienteService.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long id) {
        clienteService.eliminarCliente(id);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCliente(@PathVariable("id") Long id, Model model) {
        Optional<Cliente> optionalCliente = clienteService.obtenerClientePorId(id);

        if (optionalCliente.isEmpty()) {
            return "redirect:/clientes"; // por si no existe
        }

        model.addAttribute("cliente", optionalCliente.get());
        return "clientes/editarCliente"; // vista que mostraremos
    }

    @PostMapping("/actualizar")
    public String actualizarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "clientes/editarCliente";
        }

        clienteService.guardarCliente(cliente); // reutiliza el método de crear
        return "redirect:/clientes";
    }
}
