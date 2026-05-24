package org.example.pago.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago", nullable = false)
    private Integer id;

    // Enlace lógico con el Microservicio de Pedidos
    @NotNull(message = "El ID del pedido es obligatorio para procesar el pago")
    @Column(name = "id_pedido", nullable = false) // Asegúrate de que en tu BD se llame id_pedido o cámbialo aquí
    private Integer pedidoId;

    @NotNull(message = "El monto pagado no puede ser nulo")
    @Column(name = "monto_pagado", nullable = false)
    private Integer montoPagado;

    @Size(max = 50)
    @NotNull(message = "El método de pago no puede estar vacío")
    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;

    @NotNull
    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;
}