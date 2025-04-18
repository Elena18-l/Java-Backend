package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Exceptions.DniDuplicadoException;
import com.BackSpringBoys.Java_Backend.Exceptions.FechaNacException;
import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.Optional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"user/clientes", "admin/clientes"})
public class ClienteControlador {

    public ClienteService clienteService;

    public ClienteControlador(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
        return "clientes/listaClientes";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioAgregarCliente(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", new Cliente());
        return "clientes/agregarCliente";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "clientes/agregarCliente";
        }
        try{
            clienteService.guardarCliente(cliente);
        }catch (DniDuplicadoException e){
            bindingResult.rejectValue("dni", "error.dni", e.getMessage());
            model.addAttribute("cliente", cliente);
            return "clientes/agregarCliente";
        }catch (FechaNacException e){
            bindingResult.rejectValue("fechaNacimiento", "error.fechaNacimiento", e.getMessage());
            model.addAttribute("cliente", cliente);
            return "clientes/agregarCliente";
        } catch (Exception e) { // todo : adaptar Exceptions
            bindingResult.rejectValue("dni", "error.dni", "Error al guardar el cliente.");
            model.addAttribute("cliente", cliente);
            return "clientes/agregarCliente";
        }
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarCliente(@PathVariable("id") Long id) {
        try {
            clienteService.eliminarCliente(id);
            return "redirect:/clientes";
        } catch (Exception e) {
            return "redirect:/clientes";
        }
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioEditarCliente(@PathVariable("id") Long id, Model model) {
        try {
            Optional<Cliente> optionalCliente = clienteService.obtenerClientePorId(id);

            if (optionalCliente.isEmpty()) {
                return "redirect:/clientes";
            }

            model.addAttribute("cliente", optionalCliente.get());
            return "clientes/editarCliente";
        }catch(Exception e){
            return "redirect:/clientes";
        }
    }

    @PostMapping("/actualizar")
    @PreAuthorize("hasRole('ADMIN')")
    public String actualizarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "clientes/editarCliente";
        }

        try{
            clienteService.guardarCliente(cliente);
        }catch (DniDuplicadoException e){
            bindingResult.rejectValue("dni", "error.dni", e.getMessage());
            model.addAttribute("cliente", cliente);
            return "clientes/agregarCliente";
        }catch (FechaNacException e){
            bindingResult.rejectValue("fechaNacimiento", "error.fechaNacimiento", e.getMessage());
            model.addAttribute("cliente", cliente);
            return "clientes/agregarCliente";
        }
        return "redirect:/clientes";
    }


}
