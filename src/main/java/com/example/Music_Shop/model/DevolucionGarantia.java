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
@Table(name = "devolucion_garantia")
public class DevolucionGarantia {
    @Id
    @Column(name = "id_devolucion_garantia", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "tipo_solicitud", nullable = false, length = 50)
    private String tipoSolicitud;

    @Size(max = 300)
    @NotNull
    @Column(name = "motivo", nullable = false, length = 300)
    private String motivo;

    @Size(max = 100)
    @NotNull
    @Column(name = "estado_resolucion", nullable = false, length = 100)
    private String estadoResolucion;


}