package com.example.Music_Shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @Column(name = "id_pago", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "monto_pagado", nullable = false)
    private Integer montoPagado;

    @Size(max = 50)
    @NotNull
    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;

    @NotNull
    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;


}