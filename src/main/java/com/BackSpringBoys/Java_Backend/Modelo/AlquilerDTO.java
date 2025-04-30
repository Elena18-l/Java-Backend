package com.BackSpringBoys.Java_Backend.Modelo;

import java.time.LocalDate;

public class AlquilerDTO {

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String vehiculo;

    private String cliente;

    private long id;

    public AlquilerDTO() { }

    public AlquilerDTO(LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo, Cliente cliente, long id) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.vehiculo = vehiculo.getMatricula();
        this.cliente = cliente.getNombre() + " " + cliente.getApellido1() ;
        this.id = id;
    }

    public AlquilerDTO(Alquiler alquiler) {
        this.fechaInicio = alquiler.getFechaInicio();
        this.fechaFin = alquiler.getFechaFin();
        this.id = alquiler.getId();

        if (alquiler.getVehiculo() != null) {
            this.vehiculo = alquiler.getVehiculo().getMatricula();
        } else {
            this.vehiculo = "Vehículo no disponible";
        }

        if (alquiler.getCliente() != null) {
            String nombre = alquiler.getCliente().getNombre();
            String apellido1 = alquiler.getCliente().getApellido1();
            if (nombre != null && apellido1 != null) {
                this.cliente = nombre + " " + apellido1;
            } else {
                this.cliente = "Cliente sin nombre";
            }
        } else {
            this.cliente = "Cliente no disponible";
        }
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public long getId() {
        return id;
    }

}
