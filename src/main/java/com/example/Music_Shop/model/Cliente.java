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
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "id_cliente", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "rut", nullable = false)
    private String rut;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Size(max = 200)
    @NotNull
    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Size(max = 300)
    @NotNull
    @Column(name = "password_hash", nullable = false, length = 300)
    private String passwordHash;


}