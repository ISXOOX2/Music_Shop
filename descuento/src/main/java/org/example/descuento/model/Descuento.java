package org.example.descuento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "descuento")
public class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_descuento", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "codigo", nullable = false, length = 100)
    private String codigo;

    @NotNull
    @Column(name = "porcentaje", nullable = false)
    private Double porcentaje;

    @NotNull
    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;


}