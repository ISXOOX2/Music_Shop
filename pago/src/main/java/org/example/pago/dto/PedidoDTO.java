package org.example.pago.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PedidoDTO {

    private Integer id;
    private Integer clienteId;
    private LocalDateTime fechaEmision;
    private Integer totalFinal;
    private String estadoPedido;
}