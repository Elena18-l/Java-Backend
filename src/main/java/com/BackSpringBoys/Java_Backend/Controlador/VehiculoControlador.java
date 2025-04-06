package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Services.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/vehiculo")
public class VehiculoControlador {

    public VehiculoService vehiculoService;

    public VehiculoControlador(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public String getVehiculos(Model model) {
        model.addAttribute("vehiculos", vehiculoService.obternerTodosLosVehiculos());
        return "vehiculos/listaVehiculos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioAgregarVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        return "vehiculos/addVehiculo";
    }

    @PostMapping("/guardar")
    public String addVehiculo(@ModelAttribute Vehiculo vehiculo, BindingResult result, Model model) {
        try {
            vehiculoService.guardarVehiculo(vehiculo);
        }catch (IllegalArgumentException e){
            result.rejectValue("matricula", "error.matricula", e.getMessage());
            model.addAttribute("vehiculo", vehiculo);
            return "vehiculos/addVehiculo";
        }
        return "redirect:/vehiculo";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteVehiculo(@PathVariable("id") Long id) {
        vehiculoService.eliminarVehiculo(id);
        return "redirect:/vehiculo";
    }
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarVehiculo(@PathVariable Long id, Model model) {
        Optional<Vehiculo> optionalVehiculo = vehiculoService.obtenerVehiculoPorId(id);
        if (optionalVehiculo.isPresent()) {
            model.addAttribute("vehiculo", optionalVehiculo.get());
            return "vehiculos/editVehiculo";
        } else {
            // Redirigir a la lista o mostrar un error si no se encuentra el vehículo
            return "redirect:/vehiculo";
        }
    }



    @PostMapping("/actualizar")
    public String actualizarVehiculo(@ModelAttribute Vehiculo vehiculo) {
        vehiculoService.guardarVehiculo(vehiculo);
        return "redirect:/vehiculo";
    }

    public boolean validarMatricula(String matricula) {
        Iterable<Vehiculo> vehiculos = vehiculoService.obternerTodosLosVehiculos();
        if (vehiculos != null) {
            for (Vehiculo vehiculo : vehiculos) {
                if (vehiculo.getMatricula().equalsIgnoreCase(matricula)) {
                    return false;
                }
            }
        }else {
            return true;
        }
        return true;
    }
}
