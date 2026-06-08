package org.example.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "id_sucursal", nullable = false)
    private Integer sucursalId;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
}