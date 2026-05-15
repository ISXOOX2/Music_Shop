package com.example.Music_Shop.model;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @Size(max = 12)
    @NotNull
    @Column(name = "rut", nullable = false, length = 12)
    private String rut;

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