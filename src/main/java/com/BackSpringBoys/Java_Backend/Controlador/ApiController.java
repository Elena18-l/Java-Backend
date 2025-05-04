package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import com.BackSpringBoys.Java_Backend.Modelo.Dto.AlquilerDTO;
import com.BackSpringBoys.Java_Backend.Modelo.Dto.VehiculoDTO;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Services.AlquilerService;
import com.BackSpringBoys.Java_Backend.Services.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Obtener todos los alquileres")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de alquileres"),
            @ApiResponse(responseCode = "204", description = "No hay alquileres disponibles"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/alquileres")
    public ResponseEntity<List<AlquilerDTO>> getAllAlquileres() {
        try{
            Iterable<Alquiler> alquileres = alquilerService.obtenerTodosLosAlquileres();
            List<AlquilerDTO> alquilerList = new ArrayList<>();
            if(alquileres != null) {
                alquileres.forEach(alquiler -> alquilerList.add( new AlquilerDTO(alquiler)));
            }

            if(alquilerList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(alquilerList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(summary = "Obtener todos los vehiculos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de vehiculos"),
            @ApiResponse(responseCode = "204", description = "No hay vehiculos disponibles"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/vehiculos")
    public ResponseEntity<List<VehiculoDTO>> getAllVehiculos() {
        try{
            Iterable<Vehiculo> vehiculos = vehiculoService.obternerTodosLosVehiculos();
            List<VehiculoDTO> vehiculosList = new ArrayList<>();
            if(vehiculos != null) {
                vehiculos.forEach(vehiculo -> vehiculosList.add(new VehiculoDTO(vehiculo)));
            } else {
                System.out.println("No hay vehiculos disponibles");
            }

            if(vehiculosList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(vehiculosList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
