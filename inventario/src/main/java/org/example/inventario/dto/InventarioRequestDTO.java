package org.example.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioRequestDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    private Integer productoId;

    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Integer sucursalId;

    @NotNull(message = "La cantidad disponible es obligatoria")
    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    private Integer cantidadDisponible;

    @Min(value = 0, message = "La cantidad reservada no puede ser negativa")
    private Integer cantidadReservada = 0;
}