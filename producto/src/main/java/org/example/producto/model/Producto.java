package org.example.producto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false)
    private Integer id;

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

    @Size(max = 500)
    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Size(max = 100)
    @Column(name = "categoria", length = 100)
    private String categoria;

    @Size(max = 100)
    @Column(name = "marca", length = 100)
    private String marca;
}