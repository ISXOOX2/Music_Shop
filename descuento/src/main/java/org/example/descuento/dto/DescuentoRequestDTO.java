package org.example.descuento.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DescuentoRequestDTO {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 100, message = "El código no puede exceder los 100 caracteres")
    private String codigo;

    @NotNull(message = "El porcentaje es obligatorio")
    @DecimalMin(value = "0.1", message = "El porcentaje debe ser mayor a 0")
    @DecimalMax(value = "100.0", message = "El porcentaje no puede ser mayor a 100")
    private Double porcentaje;

    @NotNull(message = "La fecha de expiración es obligatoria")
    @Future(message = "La fecha de expiración debe ser una fecha futura")
    private LocalDateTime fechaExpiracion;
}