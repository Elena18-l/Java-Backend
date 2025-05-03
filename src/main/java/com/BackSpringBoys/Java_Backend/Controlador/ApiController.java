package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import com.BackSpringBoys.Java_Backend.Modelo.AlquilerDTO;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Services.AlquilerService;
import com.BackSpringBoys.Java_Backend.Services.VehiculoService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ApiController {

    private final VehiculoService vehiculoService;
    private final AlquilerService alquilerService;

    public ApiController(VehiculoService vehiculoService, AlquilerService alquilerService) {
        this.vehiculoService = vehiculoService;
        this.alquilerService = alquilerService;
    }

    @GetMapping("/alquileres")
    public List<AlquilerDTO> getAllAlquileres() {
        try{
            System.out.println('1');
            Iterable<Alquiler> alquileres = alquilerService.obtenerTodosLosAlquileres();
            System.out.println('2');
            List<AlquilerDTO> alquilerList = new ArrayList<>();
            System.out.println('3');
            if(alquileres != null) {
                System.out.println('4');
//                alquileres.forEach(alquiler -> alquilerList.add(new AlquilerDTO(alquiler)));
                alquileres.forEach(alquiler -> {
                    AlquilerDTO dto = new AlquilerDTO(alquiler);
                    System.out.println(dto); // Asegúrate de que AlquilerDTO tenga implementado toString()
                    alquilerList.add(dto);
                });
                System.out.println('5');
            } else {
                System.out.println('6');
                System.out.println("No hay alquileres disponibles");
            }
            System.out.println('7');
            return alquilerList;
        } catch (Exception e) {
            System.out.println('8');
            System.out.println(e.getMessage());
        }
        System.out.println('9');
        return null;
    }

    @GetMapping("/vehiculos")
    public List<Vehiculo> getAllVehiculos() {
        try{
            Iterable<Vehiculo> vehiculos = vehiculoService.obternerTodosLosVehiculos();
            List<Vehiculo> vehiculosList = new ArrayList<>();
            if(vehiculos != null) {
                vehiculos.forEach(vehiculosList::add);
            } else {
                System.out.println("No hay vehiculos disponibles");
            }
            return vehiculosList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
            return null;
    }
}
