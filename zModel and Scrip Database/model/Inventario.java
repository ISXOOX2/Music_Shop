package org.example.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "producto_id", nullable = false)
    private Integer productoId;

    @NotNull
    @Column(name = "bodega_id", nullable = false)
    private Integer bodegaId;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
}