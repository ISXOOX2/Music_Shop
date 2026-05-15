package org.example.bodega.model;     // ← package nuevo, no es el de Music_Shop

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bodega")
public class Bodega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bodega", nullable = false)
    private Integer id;

    // ⚠️ Importante: en microservicios NO se usa @ManyToOne hacia otro microservicio.
    // En vez de relación JPA con Sucursal, guardás SOLO el id como Integer.
    @NotNull
    @Column(name = "sucursal_id", nullable = false)
    private Integer sucursalId;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidadMaxima;
}