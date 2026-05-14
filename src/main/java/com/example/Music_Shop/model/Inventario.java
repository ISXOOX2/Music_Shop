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
@Table(name = "inventario")
public class Inventario {
    @Id
    @Column(name = "id_inventario", nullable = false)
    private Integer id;

    @Size(max = 200)
    @NotNull
    @Column(name = "cantidad", nullable = false, length = 200)
    private String cantidad;


}