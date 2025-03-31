package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Services.AlquilerService;
import com.BackSpringBoys.Java_Backend.Services.ClienteService;
import com.BackSpringBoys.Java_Backend.Services.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String listarAlquiler(Model model) {
        model.addAttribute("alquieres", alquilerService.obtenerTodosLosAlquileres());
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
    public String guardarAlquiler(@ModelAttribute Alquiler alquiler) {
        alquilerService.guardarAlquiler(alquiler);
        return "redirect:/alquiler";
    }

    @GetMapping("/addPrueba")
    public void addDatos(){
        Vehiculo veh1 = new Vehiculo();
        Vehiculo veh2 = new Vehiculo();
        Vehiculo veh3 = new Vehiculo();
        Vehiculo veh4 = new Vehiculo();
        Vehiculo veh5 = new Vehiculo();
        Vehiculo veh6 = new Vehiculo();


        Alquiler alquiler = new Alquiler();
    }

}
