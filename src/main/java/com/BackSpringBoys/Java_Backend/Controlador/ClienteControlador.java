 package com.BackSpringBoys.Java_Backend.Controlador;

 import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
 import com.BackSpringBoys.Java_Backend.Repositorio.ClienteRepositorio;
 import com.BackSpringBoys.Java_Backend.Services.ClienteService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.*;

 @Controller
 @RequestMapping("/clientes")
 public class ClienteControlador {

//     private ClienteRepositorio clienteRepositorio;
     public ClienteService clienteService;

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
     public String guardarCliente(@ModelAttribute Cliente cliente) {
         clienteService.guardarCliente(cliente);
         return "redirect:/clientes";
     }

     @GetMapping("/eliminar/{id}")
     public String eliminarCliente(@PathVariable("id") Long id) {
         clienteService.eliminarCliente(id);
         return "redirect:/clientes";
     }
 }
