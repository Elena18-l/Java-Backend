package com.BackSpringBoys.Java_Backend.Modelo.Dto;

import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información extendida de un alquiler, incluyendo el precio")
public class AlquilerExtraDTO extends AlquilerDTO {

    @Schema(description = "Precio total del alquiler", example = "300.0")
    private Double precio;


    public AlquilerExtraDTO() {
        super();
    }

    public AlquilerExtraDTO(Double precio, Integer idVehiculo) {
        super();
        this.precio = precio;
    }

    public AlquilerExtraDTO(Alquiler alquiler) {
        super(alquiler.getFechaInicio(), alquiler.getFechaFin(), alquiler.getVehiculo(), alquiler.getCliente(), alquiler.getId());
        this.precio = alquiler.getPrecio();
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
