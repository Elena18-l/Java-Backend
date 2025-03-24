package Modelo;

public class Vehiculo {
    private int id;
    private String matricula;
    private String modelo;
    private String marca;
    private String foto;

    public Vehiculo(int id, String matricula, String modelo, String marca, String foto) {
        this.id = id;
        this.matricula = matricula;
        this.modelo = modelo;
        this.marca = marca;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "Vehiculo{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
