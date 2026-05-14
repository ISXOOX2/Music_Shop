package com.example.Music_Shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @Column(name = "id_pedido", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @NotNull
    @Column(name = "total_final", nullable = false)
    private Integer totalFinal;

    @Size(max = 100)
    @NotNull
    @Column(name = "estado_pedido", nullable = false, length = 100)
    private String estadoPedido;


}