package com.BackSpringBoys.Java_Backend.Modelo.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;


import java.time.LocalDate;

@Schema(description = "Datos requeridos para crear un nuevo alquiler")
public class AlquilerRequestDTO {

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Schema(description = "Fecha de inicio del alquiler (formato yyyy-MM-dd)", example = "2025-05-20")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Schema(description = "Fecha de fin del alquiler (formato yyyy-MM-dd)", example = "2025-05-25")
    private LocalDate fechaFin;

    @NotBlank(message = "La matrícula es obligatoria")
    @Pattern(regexp = "\\d{4}[A-Z]{3}", message = "La matrícula debe tener el formato 1111XXX")
    @Schema(description = "Matrícula del vehículo, en formato 1111XXX", example = "1234ABC")
    private String matriculaVehiculo;

    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    @Schema(description = "Precio total del alquiler", example = "250.0")
    private double precio;

    // Getters y Setters
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMatriculaVehiculo() {
        return matriculaVehiculo;
    }

    public void setMatriculaVehiculo(String matriculaVehiculo) {
        this.matriculaVehiculo = matriculaVehiculo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
