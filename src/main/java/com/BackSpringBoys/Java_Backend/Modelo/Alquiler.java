package com.BackSpringBoys.Java_Backend.Modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
public class Alquiler {

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro")
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

//    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser en el futuro")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;


    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    @NotNull(message = "Debe seleccionar un vehículo")
    @JsonBackReference
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @NotNull(message = "Debe seleccionar un cliente")
    @JsonBackReference
    private Cliente cliente;

    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    @Column(nullable = false)
    private double precio;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /*
    métodos sin validación
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

     */

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

    @AssertTrue(message = "La fecha de inicio debe ser anterior a la fecha de fin")
    public boolean isFechaValida()
    {
        if (fechaInicio != null && fechaFin != null) {
            return fechaInicio.isBefore(fechaFin);
        }
        return false;
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
