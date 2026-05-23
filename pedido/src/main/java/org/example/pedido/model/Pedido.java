package org.example.pedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @Column(name = "descuento_id")
    private Integer descuentoId;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;

    @NotNull
    @Column(name = "total_final", nullable = false)
    private Integer totalFinal;

    @Size(max = 100)
    @NotNull
    @Column(name = "estado_pedido", nullable = false, length = 100)
    private String estadoPedido;
}