package com.BackSpringBoys.Java_Backend.Modelo;

import jakarta.persistence.*;


@Entity
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 7)
    private String matricula;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    //Esto es un str con la URL de la foto? o un blob con la imagen?
    @Column()
    private String foto;


    public Vehiculo() {
    }

    public Vehiculo(long id, String matricula, String modelo, String marca, String foto) {
        this.id = id;
        this.matricula = matricula;
        this.modelo = modelo;
        this.marca = marca;
        this.foto = foto;
    }

    public Vehiculo(String matricula, String modelo, String marca, String foto) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.marca = marca;
        this.foto = foto;
    }

    public Vehiculo(String matricula, String modelo, String marca) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.marca = marca;
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Vehiculo " + id + ", Matricula: " + matricula + ", Modelo: " + modelo + ", Marca: '" + marca + ", Foto: " + foto + ".";
    }
}
