package org.example.devolucionGarantia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DevolucionGarantiaDTO {

    @NotBlank(message = "El tipo de solicitud (DEVOLUCION/GARANTIA) es obligatorio")
    @Size(max = 50)
    private String tipoSolicitud;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 300)
    private String motivo;

    @NotNull(message = "El ID del pedido es obligatorio")
    private Integer idPedido;

    @NotNull(message = "El ID del inventario es obligatorio")
    private Integer idInventario;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad a devolver debe ser mínimo 1")
    private Integer cantidad;
}
