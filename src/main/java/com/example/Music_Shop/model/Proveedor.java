package com.example.Music_Shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor", nullable = false)
    private Integer id;

    @Size(max = 12)
    @NotNull
    @Column(name = "rut", nullable = false, length = 12)
    private String rut;

    @Size(max = 200)
    @Column(name = "razon_social", length = 200)
    private String razonSocial;

    @Size(max = 200)
    @NotNull
    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Size(max = 50)
    @NotNull
    @Column(name = "telefono", nullable = false, length = 50)
    private String telefono;


}