package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Repositorio.UsuarioRepositorio;
import com.BackSpringBoys.Java_Backend.Services.AlquilerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/secure")
public class AuthApiController {
    @Autowired
    private AlquilerService alquilerService;
    private final UsuarioRepositorio usuarioRepositorio;

    public AuthApiController(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Operation(summary = "Obtener el usuario actual")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
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


    @Operation(summary = "Obtener el historial de alquileres del usuario autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/historial")
    public ResponseEntity<List<Alquiler>> getHistorial(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            Usuario usuario = usuarioRepositorio.findByUsername(userDetails.getUsername()).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }


            List<Alquiler> historial = alquilerService.obtenerAlquileresPorCliente(usuario.getCliente());
            return ResponseEntity.ok(historial);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}