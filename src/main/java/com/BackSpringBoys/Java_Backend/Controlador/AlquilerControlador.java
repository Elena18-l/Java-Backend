package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Services.AlquilerService;
import com.BackSpringBoys.Java_Backend.Services.ClienteService;
import com.BackSpringBoys.Java_Backend.Services.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/alquiler")
public class AlquilerControlador {

    private final VehiculoService vehiculoService;
    private final ClienteService clienteService;
    public AlquilerService alquilerService;

    public AlquilerControlador(AlquilerService alquilerService, VehiculoService vehiculoService, ClienteService clienteService) {
        this.alquilerService = alquilerService;
        this.vehiculoService = vehiculoService;
        this.clienteService = clienteService;
    }

    /* debug alquiler
    @GetMapping
    public String listarAlquiler(Model model) {
        model.addAttribute("alquileres", alquilerService.obtenerTodosLosAlquileres());
        return "alquiler/listaAlquiler";
    } */

    @GetMapping
    public String listarAlquiler(Model model) {
        System.out.println("Intentando cargar lista de alquileres...");
        var alquileres = alquilerService.obtenerTodosLosAlquileres();
        alquileres.forEach(a -> System.out.println(a));

        model.addAttribute("alquileres", alquileres);
        return "alquiler/listaAlquiler";
    }

    @GetMapping("/{id}")
    public String alquiler(@RequestParam(name = "id", defaultValue = "0") Long id, Model model) {
        if (id == 0) {
            return "redirect:/alquiler";
        }
        model.addAttribute("alquiler", alquilerService.obtenerAlquilerPorId(id));
        return "alquiler/alquiler";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioAgregarAlquiler(Model model) {
        model.addAttribute("alquiler", new Alquiler());
        model.addAttribute("vehiculos", vehiculoService.obternerTodosLosVehiculos());
        model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
        return "alquiler/addAlquiler";
    }

    @PostMapping("/guardar")
    public String guardarAlquiler(@Valid @ModelAttribute Alquiler alquiler, BindingResult result, Model model) {
        if (result.hasErrors()) {
            if (result.hasGlobalErrors()) {
                for (ObjectError error : result.getGlobalErrors()) {
                    System.out.println("ERROR GLOBAL: " + error.getDefaultMessage());
                }
            }

            if (result.hasFieldErrors()) {
                for (FieldError fieldError : result.getFieldErrors()) {
                    System.out.println("ERROR DE CAMPO: " + fieldError.getField() + " - " + fieldError.getDefaultMessage());
                }
            }

            System.out.println(result.getFieldError());
            model.addAttribute("vehiculos", vehiculoService.obternerTodosLosVehiculos());
            model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
            return "alquiler/addAlquiler";
        }
        alquilerService.guardarAlquiler(alquiler);
        return "redirect:/alquiler";
    }

    @GetMapping("/addPrueba")
    public String addDatos(){
        Vehiculo veh1 = new Vehiculo( "1235ABC", "Megane", "Renault", "");
        Vehiculo veh2 = new Vehiculo( "5678DEF", "Clio", "Renault", "");
        Vehiculo veh3 = new Vehiculo( "9101GHI", "Astra", "Opel", "");
        Vehiculo veh4 = new Vehiculo( "1121JKL", "Corsa", "Opel", "");
        Vehiculo veh5 = new Vehiculo( "3141MNO", "Ibiza", "Seat", "");
        Vehiculo veh6 = new Vehiculo( "5161PQR", "Leon", "Seat", "");
        LocalDate fec1 = LocalDate.now();
        LocalDate fec2 = LocalDate.of(2000, 10, 10);
        LocalDate fec3 = LocalDate.of(1999, 10, 2);
        LocalDate fec4 = LocalDate.of(1998, 11, 10);
        LocalDate fec5 = LocalDate.of(1997, 4, 15);
        LocalDate fec6 = LocalDate.of(1996, 5, 20);
        LocalDate fec7 = LocalDate.of(1995, 6, 25);
        LocalDate fec8 = LocalDate.of(1994, 7, 30);
        LocalDate fec9 = LocalDate.of(1993, 8, 5);
        LocalDate fec10 = LocalDate.of(1992, 9, 10);
        Cliente cli1 = new Cliente("Juan", "Perez", "12345678A", "Calle Falsa 123", fec1);
        Cliente cli2 = new Cliente( "Pedro", "Garcia", "87654321B", "Calle Falsa 321", fec2);
        Cliente cli3 = new Cliente( "Maria", "Lopez", "12348765C", "Calle Falsa 132", fec3);
        Cliente cli4 = new Cliente( "Ana", "Martinez", "56781234D", "Calle Falsa 213", fec4);
        Cliente cli5 = new Cliente( "Luis", "Sanchez", "87654321E", "Calle Falsa 312", fec5);
        Cliente cli6 = new Cliente( "Sara", "Gomez", "12348765F", "Calle Falsa 231", fec6);
        Cliente cli7 = new Cliente( "Pablo", "Rodriguez", "56781234G", "Calle Falsa 321", fec7);
        Cliente cli8 = new Cliente( "Laura", "Hernandez", "87654321H", "Calle Falsa 123", fec8);
        Cliente cli9 = new Cliente( "Carlos", "Fernandez", "12348765I", "Calle Falsa 132", fec9);
        Cliente cli10 = new Cliente( "Sonia", "Garcia", "56781234J", "Calle Falsa 213", fec10);

        vehiculoService.guardarVehiculo(veh1);
        vehiculoService.guardarVehiculo(veh2);
        vehiculoService.guardarVehiculo(veh3);
        vehiculoService.guardarVehiculo(veh4);
        vehiculoService.guardarVehiculo(veh5);
        vehiculoService.guardarVehiculo(veh6);
        clienteService.guardarCliente(cli1);
        clienteService.guardarCliente(cli2);
        clienteService.guardarCliente(cli3);
        clienteService.guardarCliente(cli4);
        clienteService.guardarCliente(cli5);
        clienteService.guardarCliente(cli6);
        clienteService.guardarCliente(cli7);
        clienteService.guardarCliente(cli8);
        clienteService.guardarCliente(cli9);
        clienteService.guardarCliente(cli10);

        Alquiler alq1 = new Alquiler(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 7), veh1, cli1, 150.00);
        Alquiler alq2 = new Alquiler(LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 20), veh2, cli2, 200.00);
        Alquiler alq3 = new Alquiler(LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 22), veh3, cli3, 180.00);
        Alquiler alq4 = new Alquiler(LocalDate.of(2025, 3, 5), LocalDate.of(2025, 3, 12), veh4, cli4, 160.00);
        Alquiler alq5 = new Alquiler(LocalDate.of(2025, 3, 3), LocalDate.of(2025, 3, 10), veh5, cli5, 170.00);
        Alquiler alq6 = new Alquiler(LocalDate.of(2025, 2, 25), LocalDate.of(2025, 3, 2), veh6, cli6, 190.00);
        Alquiler alq7 = new Alquiler(LocalDate.of(2025, 1, 20), LocalDate.of(2025, 1, 25), veh1, cli7, 140.00);
        Alquiler alq8 = new Alquiler(LocalDate.of(2025, 2, 5), LocalDate.of(2025, 2, 15), veh2, cli8, 210.00);
        Alquiler alq9 = new Alquiler(LocalDate.of(2025, 3, 10), LocalDate.of(2025, 3, 17), veh3, cli9, 185.00);
        Alquiler alq10 = new Alquiler(LocalDate.of(2025, 1, 25), LocalDate.of(2025, 2, 5), veh4, cli10, 200.00);
        Alquiler alq11 = new Alquiler(LocalDate.of(2025, 3, 8), LocalDate.of(2025, 3, 14), veh5, cli1, 160.00);
        Alquiler alq12 = new Alquiler(LocalDate.of(2025, 2, 18), LocalDate.of(2025, 2, 24), veh6, cli2, 190.00);
        Alquiler alq13 = new Alquiler(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 5), veh1, cli3, 130.00);
        Alquiler alq14 = new Alquiler(LocalDate.of(2025, 1, 30), LocalDate.of(2025, 2, 4), veh2, cli4, 170.00);
        Alquiler alq15 = new Alquiler(LocalDate.of(2025, 2, 22), LocalDate.of(2025, 3, 1), veh3, cli5, 180.00);

        alquilerService.guardarAlquiler(alq1);
        alquilerService.guardarAlquiler(alq2);
        alquilerService.guardarAlquiler(alq3);
        alquilerService.guardarAlquiler(alq4);
        alquilerService.guardarAlquiler(alq5);
        alquilerService.guardarAlquiler(alq6);
        alquilerService.guardarAlquiler(alq7);
        alquilerService.guardarAlquiler(alq8);
        alquilerService.guardarAlquiler(alq9);
        alquilerService.guardarAlquiler(alq10);
        alquilerService.guardarAlquiler(alq11);
        alquilerService.guardarAlquiler(alq12);
        alquilerService.guardarAlquiler(alq13);
        alquilerService.guardarAlquiler(alq14);
        alquilerService.guardarAlquiler(alq15);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarAlquiler(@PathVariable("id") Long id, Model model) {
        Optional<Alquiler> alquilerOptional = alquilerService.obtenerAlquilerPorId(id);
        if (alquilerOptional.isPresent()) {
            model.addAttribute("alquiler", alquilerOptional.get());
            model.addAttribute("vehiculos", vehiculoService.obternerTodosLosVehiculos());
            model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
            return "alquiler/editAlquiler";
        } else {
            return "redirect:/alquiler";
        }
    }

    @PostMapping("/actualizar")
    public String actualizarAlquiler(@Valid @ModelAttribute Alquiler alquiler, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("vehiculos", vehiculoService.obternerTodosLosVehiculos());
            model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
            return "alquiler/editAlquiler";
        }
        alquilerService.guardarAlquiler(alquiler);
        return "redirect:/alquiler";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable("id") Long id) {
        alquilerService.eliminarAlquiler(id);
        return "redirect:/alquiler";
    }

}
