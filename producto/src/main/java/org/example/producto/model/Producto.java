package org.example.producto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Size(max = 200)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Size(max = 100)
    @NotNull
    @Column(name = "formato", nullable = false, length = 100)
    private String formato;

    @NotNull
    @Column(name = "precio", nullable = false)
    private Integer precio;


}