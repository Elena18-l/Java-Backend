package com.BackSpringBoys.Java_Backend.Modelo.Dto;

import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;

public class VehiculoDTO {

    private long id;
    private String matricula;
    private String marca;
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

