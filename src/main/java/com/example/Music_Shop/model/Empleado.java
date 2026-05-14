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
@Table(name = "empleado")
public class Empleado {
    @Id
    @Column(name = "id_empleado", nullable = false)
    private Integer id;

    @Size(max = 200)
    @NotNull
    @Column(name = "cargo", nullable = false, length = 200)
    private String cargo;

    @NotNull
    @Column(name = "salario", nullable = false)
    private Integer salario;

    @Size(max = 200)
    @NotNull
    @Column(name = "nombre_completo", nullable = false, length = 200)
    private String nombreCompleto;


}