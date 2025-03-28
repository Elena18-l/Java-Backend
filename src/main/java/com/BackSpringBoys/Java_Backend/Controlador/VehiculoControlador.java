package com.BackSpringBoys.Java_Backend.Controlador;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class VehiculoControlador {

    @GetMapping("/vehiculo")
    public String getVehiculos(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
