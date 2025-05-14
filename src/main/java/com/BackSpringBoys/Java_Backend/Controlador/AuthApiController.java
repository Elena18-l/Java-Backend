package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Dto.AlquilerDTO;
import com.BackSpringBoys.Java_Backend.Modelo.Dto.AlquilerExtraDTO;
import com.BackSpringBoys.Java_Backend.Modelo.Dto.AlquilerRequestDTO;
import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Repositorio.UsuarioRepositorio;
import com.BackSpringBoys.Java_Backend.Services.AlquilerService;
import com.BackSpringBoys.Java_Backend.Services.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/secure")
public class AuthApiController {
    @Autowired
    private AlquilerService alquilerService;
    private final VehiculoService vehiculoService;
    private final UsuarioRepositorio usuarioRepositorio;

    public AuthApiController(UsuarioRepositorio usuarioRepositorio, VehiculoService vehiculoService) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.vehiculoService = vehiculoService;
    }

    @Operation(summary = "Obtener el usuario autenticado", description = "Devuelve los datos del usuario autenticado basado en el token JWT.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "{\"error\": \"Usuario no encontrado\"}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(example = "{\"error\": \"Error interno del servidor\"}")))
    })
    @GetMapping("/usuario")
    public ResponseEntity<Usuario> getUsuarioActual(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            Usuario usuario = usuarioRepositorio.findByUsername(userDetails.getUsername())
                    .orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Obtener historial de alquileres del usuario", description = "Devuelve todos los alquileres realizados por el usuario autenticado.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial encontrado",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AlquilerExtraDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "{\"error\": \"Usuario no encontrado\"}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(example = "{\"error\": \"Error interno del servidor\"}")))
    })
    @GetMapping("/historial")
    public ResponseEntity<List<AlquilerExtraDTO>> getHistorial(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            Usuario usuario = usuarioRepositorio.findByUsername(userDetails.getUsername()).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }

            List<Alquiler> historial = alquilerService.obtenerAlquileresPorCliente(usuario.getCliente());
            List<AlquilerExtraDTO> alquilerList = new ArrayList<>();
            for (Alquiler alq : historial) {
                alquilerList.add(new AlquilerExtraDTO(alq));
            }

            return ResponseEntity.ok(alquilerList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Obtener alquileres de un vehículo", description = "Devuelve todos los alquileres asociados a un vehículo por su matrícula.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de alquileres encontrado",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AlquilerExtraDTO.class)))),
            @ApiResponse(responseCode = "204", description = "No hay alquileres para este vehículo"),
            @ApiResponse(responseCode = "400", description = "Matrícula no válida",
                    content = @Content(schema = @Schema(example = "{\"error\": \"La matrícula es obligatoria\"}"))),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado",
                    content = @Content(schema = @Schema(example = "{\"error\": \"Vehiculo no encontrado\"}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(example = "{\"error\": \"Error interno del servidor\"}")))
    })
    @GetMapping("/vehiculo/{matricula}")
    public ResponseEntity<?> getAlquileresPorVehiculo(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails, @PathVariable String matricula) {
        try {
            if (matricula == null || matricula.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "La fecha de fin no puede ser anterior a la fecha de inicio"));
            }
            Vehiculo veh = vehiculoService.obtenerVehiculoPorMatricula(matricula)
                    .orElse(null);
            if (veh == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Vehiculo no encontrado"));
            }
            List<Alquiler> alquileres = alquilerService.findByVehiculo(veh);
            if (alquileres.isEmpty() ) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(alquileres);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Crear un nuevo alquiler",
            description = "Permite a un usuario autenticado crear un alquiler. Requiere una matrícula válida, fechas correctas y un precio mayor a 0.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alquiler creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AlquilerExtraDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"La fecha de fin no puede ser anterior a la fecha de inicio\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario o vehículo no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"Vehículo no encontrado\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"Error interno del servidor\"}")
                    )
            )
    })
    @PostMapping("/crearAlquiler")
    public ResponseEntity<?> crearAlquiler(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails, @RequestBody AlquilerRequestDTO alquilerRequestDTO) {
        try {
            Usuario usuario = usuarioRepositorio.findByUsername(userDetails.getUsername()).orElse(null);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Usuario no encontrado"));
            }
            Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorMatricula(alquilerRequestDTO.getMatriculaVehiculo())
                    .orElse(null);

            if (vehiculo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Vehículo no encontrado"));
            }

            if (alquilerRequestDTO.getFechaFin().isBefore(alquilerRequestDTO.getFechaInicio())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "La fecha de fin no puede ser anterior a la fecha de inicio"));
            }

            if (alquilerRequestDTO.getFechaInicio().isBefore(LocalDate.now())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "La fecha de  inicio no puede ser anterior a la actual"));
            }

            if (alquilerRequestDTO.getPrecio() <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "El precio debe ser mayor a 0"));
            }

            Alquiler alquiler = new Alquiler();
            alquiler.setCliente(usuario.getCliente());
            alquiler.setVehiculo(vehiculo);
            alquiler.setFechaInicio(alquilerRequestDTO.getFechaInicio());
            alquiler.setFechaFin(alquilerRequestDTO.getFechaFin());
            alquiler.setPrecio(alquilerRequestDTO.getPrecio());

            Alquiler nuevoAlquiler = alquilerService.guardarAlquiler(alquiler);
            AlquilerExtraDTO alquilerDTO = new AlquilerExtraDTO(nuevoAlquiler);
            System.out.println("Alquiler creado: " + nuevoAlquiler);
            return ResponseEntity.ok(alquilerDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}