package org.example.pago.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PagoRequestDTO {

    @NotNull(message = "El ID del pedido es obligatorio")
    private Integer pedidoId;

    @NotNull(message = "El monto pagado es obligatorio")
    @Min(value = 1, message = "El monto pagado debe ser mayor a 0")
    private Integer montoPagado;

    @NotBlank(message = "El método de pago es obligatorio")
    @Size(max = 50, message = "El método de pago no puede exceder los 50 caracteres")
    private String metodoPago;
}