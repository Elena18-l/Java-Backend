package com.BackSpringBoys.Java_Backend.Modelo;

import jakarta.persistence.*;
import java.time.*;

@Entity
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(nullable = false)
    private double precio;

    public Alquiler() {
    }

    public Alquiler(long id, LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo, Cliente cliente, double precio) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.vehiculo = vehiculo;
        this.cliente = cliente;
        this.precio = precio;
    }

    public Alquiler(LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo, Cliente cliente, double precio) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.vehiculo = vehiculo;
        this.cliente = cliente;
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    @Override
    public String toString() {
        return "Alquiler "+ id +
                ", Entrega: " + fechaInicio +
                ", Devolución: " + fechaFin +
                ", Vehiculo: " + vehiculo.getMatricula() +
                ", Cliente: " + cliente.getDni() +
                ", Precio: " + precio + ".\n";
    }
}
