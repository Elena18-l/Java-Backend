package com.BackSpringBoys.Java_Backend.Modelo.Dto;

import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos básicos de un vehículo")
public class VehiculoDTO {

    @Schema(description = "Identificador único del vehículo", example = "5")
    private long id;

    @Schema(description = "Matrícula del vehículo", example = "1234ABC")
    private String matricula;

    @Schema(description = "Marca del vehículo", example = "Toyota")
    private String marca;

    @Schema(description = "Modelo del vehículo", example = "Corolla")
    private String modelo;

    public VehiculoDTO() {
    }

    public VehiculoDTO(Vehiculo vehiculo) {
        this.id = vehiculo.getId();
        this.matricula = vehiculo.getMatricula();
        this.marca = vehiculo.getMarca();
        this.modelo = vehiculo.getModelo();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}

