package org.example.pedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor  // Constructor vacío para Hibernate
@AllArgsConstructor // Constructor con todo
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido", nullable = false)
    private Integer id;

    // Agregamos el enlace lógico con el microservicio de Clientes
    @NotNull(message = "El ID del cliente es obligatorio para el flujo de negocio")
    @Column(name = "id_cliente", nullable = false)
    private Integer clienteId;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;

    @NotNull
    @Column(name = "total_final", nullable = false)
    private Integer totalFinal;

    @Size(max = 100)
    @NotNull
    @Column(name = "estado_pedido", nullable = false, length = 100)
    private String estadoPedido;
}