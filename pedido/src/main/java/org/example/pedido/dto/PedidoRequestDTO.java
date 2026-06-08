package org.example.pedido.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class PedidoRequestDTO {

    @NotNull(message = "El total final no puede ser nulo")
    @Min(value = 1, message = "El total final debe ser mayor a 0")
    private Integer totalFinal;

    @NotBlank(message = "El estado del pedido no puede estar vacío")
    @Size(max = 100, message = "El estado del pedido no puede exceder los 100 caracteres")
    private String estadoPedido;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer clienteId;
}