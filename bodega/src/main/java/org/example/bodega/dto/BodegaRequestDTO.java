package org.example.bodega.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BodegaRequestDTO {

    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Integer sucursalId;

    @NotBlank(message = "El nombre de la bodega es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotNull(message = "La capacidad máxima es obligatoria")
    @Min(value = 1, message = "La capacidad máxima debe ser de al menos 1")
    private Integer capacidadMaxima;
}