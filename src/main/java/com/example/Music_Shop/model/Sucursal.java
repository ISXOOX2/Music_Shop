package com.example.Music_Shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sucursal")
public class Sucursal {
    @Id
    @Column(name = "id_sucursal", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 200)
    @NotNull
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @Size(max = 15)
    @NotNull
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;


}