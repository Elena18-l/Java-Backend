package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Exceptions.MatriculaRegException;
import com.BackSpringBoys.Java_Backend.Exceptions.MatriculaRepetidaException;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Services.VehiculoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping({"user/vehiculo", "admin/vehiculo"})
public class VehiculoControlador {

    public VehiculoService vehiculoService;

    public VehiculoControlador(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getVehiculos(Model model) {
        model.addAttribute("vehiculos", vehiculoService.obternerTodosLosVehiculos());
        return "vehiculos/listaVehiculos";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioAgregarVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        return "vehiculos/addVehiculo";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public String addVehiculo(@ModelAttribute Vehiculo vehiculo, BindingResult result, Model model) {
        try {
            if (!validarMatricula(vehiculo.getMatricula())) {
                throw new MatriculaRegException("La matrícula no es válida");
            }
            vehiculoService.guardarVehiculo(vehiculo);
        }catch (MatriculaRepetidaException e){
            result.rejectValue("matricula", "error.matricula", e.getMessage());
            model.addAttribute("vehiculo", vehiculo);
            return "vehiculos/addVehiculo";
        } catch (MatriculaRegException e){
            result.rejectValue("matricula", "error.matricula", e.getMessage());
            model.addAttribute("vehiculo", vehiculo);
            return "vehiculos/addVehiculo";
        }
        return "redirect:/admin/vehiculo";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String deleteVehiculo(@PathVariable("id") Long id) {
        try{
            vehiculoService.eliminarVehiculo(id);
            return "redirect:/vehiculo";
        }catch (IllegalArgumentException e){
            return "redirect:/admin/vehiculo";
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarVehiculo(@PathVariable Long id, Model model) {
        try{
            Optional<Vehiculo> optionalVehiculo = vehiculoService.obtenerVehiculoPorId(id);
            if (optionalVehiculo.isPresent()) {
                model.addAttribute("vehiculo", optionalVehiculo.get());
                return "vehiculos/editVehiculo";
            } else {
                return "redirect:/admin/vehiculo";
            }
        }catch (Exception e){
            return "redirect:/admin/vehiculo";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/actualizar")
    public String actualizarVehiculo(@ModelAttribute Vehiculo vehiculo, BindingResult result, Model model) {
        try{
            vehiculoService.guardarVehiculo(vehiculo);
        }catch (IllegalArgumentException e){
            result.rejectValue("matricula", "error.matricula", e.getMessage());
            model.addAttribute("vehiculo", vehiculo);
            return "vehiculos/addVehiculo";
        }
        return "redirect:/admin/vehiculo";
    }

    public boolean validarMatricula(String matricula) {
        String regex = "^\\d{4}[B-DF-HJ-NP-TV-Z]{3}$";
        return matricula.matches(regex);
    }
}
