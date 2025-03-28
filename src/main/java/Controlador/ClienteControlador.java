package Controlador;

import Modelo.Cliente;
import Repositorio.ClienteRepositorio;
import Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    private ClienteService clienteService;

    // Mostrar todos los clientes
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepositorio.findAll());
        return "clientes/listaClientes";  // vista Thymeleaf: src/main/resources/templates/clientes/listaClientes.html
    }

    // Mostrar el formulario para agregar un cliente
    @GetMapping("/nuevo")
    public String mostrarFormularioAgregarCliente(Model model) {
        Cliente cliente = new Cliente();  // Cliente vacío para el formulario
        model.addAttribute("cliente", cliente);
        return "clientes/agregarCliente";  // vista Thymeleaf: src/main/resources/templates/clientes/agregarCliente.html
    }

    // Guardar el nuevo cliente
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        clienteRepositorio.save(cliente);  // Guardamos el cliente en la base de datos
        return "redirect:/clientes";  // Redirigimos a la lista de clientes
    }

    // Eliminar un cliente
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long id) {
        clienteRepositorio.deleteById(id);  // Eliminamos el cliente
        return "redirect:/clientes";  // Redirigimos a la lista de clientes
    }
}
