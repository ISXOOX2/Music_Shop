package org.example.devolucionGarantia.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devolucion_garantia")
public class DevolucionGarantia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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